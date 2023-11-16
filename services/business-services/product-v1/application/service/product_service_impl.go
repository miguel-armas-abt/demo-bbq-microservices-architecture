package service

import (
	"gorm.io/gorm"
	"product-v1/domain/exception"
	"product-v1/domain/model/request"
	"product-v1/domain/model/response"
	"product-v1/infrastructure/mapper"
	"product-v1/infrastructure/repository"
)

type productServiceImpl struct {
	repository repository.ProductRepository
}

func NewProductService(productRepository repository.ProductRepository) ProductService {
	return &productServiceImpl{
		repository: productRepository,
	}
}

func (thisService *productServiceImpl) FindAll() ([]response.ProductResponse, error) {
	productListFound, err := thisService.repository.FindAll()
	if err != nil {
		return nil, err
	}
	return mapper.EntityListToResponseList(productListFound), nil
}

func (thisService *productServiceImpl) FindById(id uint) (*response.ProductResponse, error) {
	productFound, err := thisService.repository.FindById(id)
	if err != nil {
		if err == gorm.ErrRecordNotFound {
			return nil, exception.ProductNotFound
		}
		return nil, err
	}
	product := mapper.EntityToResponse(*productFound)
	return &product, nil
}

func (thisService *productServiceImpl) FindByScope(scope string) ([]response.ProductResponse, error) {
	productListFound, err := thisService.repository.FindByScope(scope)
	if err != nil {
		return nil, err
	}

	return mapper.EntityListToResponseList(productListFound), nil
}

func (thisService *productServiceImpl) Save(productRequest request.ProductRequest) (*response.ProductResponse, error) {
	productToSave := mapper.RequestToEntity(productRequest)
	if err := thisService.repository.Save(&productToSave); err != nil {
		return nil, err
	}
	product := mapper.EntityToResponse(productToSave)
	return &product, nil
}

func (thisService *productServiceImpl) Update(productRequest request.ProductRequest, id uint) (*response.ProductResponse, error) {
	productFound, err := thisService.repository.FindById(id)
	if err != nil {
		return nil, err
	}
	mapper.UpdateRequestToEntity(productRequest, productFound)

	if err = thisService.repository.Save(productFound); err != nil {
		return nil, err
	}

	product := mapper.EntityToResponse(*productFound)
	return &product, nil
}

func (thisService *productServiceImpl) Delete(id uint) error {
	return thisService.repository.Delete(id)
}
