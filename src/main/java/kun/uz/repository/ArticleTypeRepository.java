package kun.uz.repository;

import jakarta.annotation.security.PermitAll;
import jakarta.transaction.Transactional;
import kun.uz.dto.NameInterface;
import kun.uz.entity.ArticleTypeEntity;
import org.apache.catalina.LifecycleState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArticleTypeRepository extends CrudRepository<ArticleTypeEntity,Integer>{
    @Modifying
    @Transactional
    @Query(value = "delete From ArticleTypeEntity a where a.id = ?1")
    int deleteArticleType(Integer id);

    @Query(value = "From ArticleTypeEntity order by createdDate asc")
    Page<ArticleTypeEntity>getAll(Pageable pageable);

    @Query("SELECT a.id as id, " +
            "CASE WHEN :lang = 'uz' THEN a.nameUz " +
            "WHEN :lang = 'ru' THEN a.nameRu " +
            "WHEN :lang = 'en' THEN a.nameEn " +
            "END as name " +
            "FROM ArticleTypeEntity a WHERE a.visible = true")
    List<NameInterface> getByLang(@Param("lang") String lang);




}
