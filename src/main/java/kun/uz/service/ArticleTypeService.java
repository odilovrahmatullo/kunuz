package kun.uz.service;

import kun.uz.dto.article.ArticleTypeDTO;
import kun.uz.dto.base.NameInterface;
import kun.uz.entity.ArticleTypeEntity;
import kun.uz.exceptions.ResourceNotFoundException;
import kun.uz.repository.ArticleTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Service
public class ArticleTypeService {
    @Autowired
    private ArticleTypeRepository articleTypeRepository;
    @Autowired
    private ResourceBundleService resourceBundleService;

    public ArticleTypeDTO create(ArticleTypeDTO dto) {
        ArticleTypeEntity entity = new ArticleTypeEntity();
        entity.setOrderNumber(dto.getOrderNumber());
        entity.setNameUz(dto.getNameUz());
        entity.setNameEn(dto.getNameEn());
        entity.setNameRu(dto.getNameRu());
        entity.setCreatedDate(LocalDateTime.now());
        entity.setVisible(true);
        articleTypeRepository.save(entity);
        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public String update(Integer id, ArticleTypeDTO dto, String lang) {
        ArticleTypeEntity entity = articleTypeRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException(resourceBundleService.getMessage("post.not.found",lang)));

        if(dto.getNameUz()!=null){
            entity.setNameUz(dto.getNameUz());
        }
        if(dto.getNameEn()!=null){
            entity.setNameEn(dto.getNameEn());
        }
        if(dto.getNameRu()!=null){
            entity.setNameRu(dto.getNameRu());
        }
        if(dto.getOrderNumber()!=null){
            entity.setOrderNumber(dto.getOrderNumber());
        }

        articleTypeRepository.save(entity);
        return "UPDATED";
    }

    public String delete(Integer id, String lang) {
        if(articleTypeRepository.deleteArticleType(id)==1){
            return "DELETED";
        }
        throw new  ResourceNotFoundException(resourceBundleService.getMessage("post.not.found",lang));
    }

    public PageImpl<ArticleTypeDTO> getAll(int page, int size) {
//        PageRequest pageRequest = PageRequest.of(page, size);
//        Page<ArticleTypeEntity> pageEntity = articleRepository.getAll(pageRequest);
//        List<ArticleTypeDTO> pageDTO = changeToDTOList(articleRepository.getAll(pageRequest).getContent());
//        long total = pageEntity.getTotalElements();
//        return new PageImpl(pageDTO, pageRequest, total);
        return helper(PageRequest.of(page, size),articleTypeRepository.getAll(PageRequest.of(page, size)));
    }

    //public

    public ArticleTypeDTO changeToDTO(ArticleTypeEntity entity) {
        ArticleTypeDTO dto = new ArticleTypeDTO();
        dto.setId(entity.getId());
        dto.setNameUz(entity.getNameUz());
        dto.setNameEn(entity.getNameEn());
        dto.setNameRu(entity.getNameRu());
        dto.setOrderNumber(entity.getOrderNumber());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public List<NameInterface> getByLang(String lang) {
        return articleTypeRepository.getByLang(lang);
    }

    public List<ArticleTypeDTO>changeToDTOList(List<ArticleTypeEntity> entityList) {
        List<ArticleTypeDTO> dtoList = new LinkedList<>();
        for(ArticleTypeEntity entity : entityList){
            dtoList.add(changeToDTO(entity));
        }
        return dtoList;
    }

    public PageImpl<ArticleTypeDTO> helper(Pageable pageable,Page<ArticleTypeEntity> entityPage) {
//    List<ArticleTypeEntity> entityList = entityPage.getContent();
//    List<ArticleTypeDTO> dtoList = changeToDTOList(entityList);
//    long total = entityPage.getTotalElements();
    return new PageImpl<>(changeToDTOList(entityPage.getContent()), pageable, entityPage.getTotalElements());
    }

    public ArticleTypeDTO getById(Integer id, String lang) {
        return changeToDTO(articleTypeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(resourceBundleService.getMessage("post.not.found",lang))));
    }

}
