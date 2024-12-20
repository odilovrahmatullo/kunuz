package kun.uz.controller.article;

import jakarta.servlet.http.HttpServletRequest;
import kun.uz.dto.article.*;
import kun.uz.enums.AppLanguage;
import kun.uz.enums.LikeStatus;
import kun.uz.service.ArticleService;
import kun.uz.util.HeaderUtil;
import kun.uz.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @PostMapping("create")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> create(@RequestBody ArticleCreationDTO dto){
        return ResponseEntity.ok(articleService.createArticle(dto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<?> update(@PathVariable String id,
                                    @RequestBody UpdateArticleDTO dto,
                                    @RequestHeader(value = "Accepted-Language", defaultValue = "uz") AppLanguage lang){
        return ResponseEntity.ok(articleService.update(id,dto,lang.name()));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<?> getById(@PathVariable String id,
                                     @RequestHeader(value = "Accepted-Language", defaultValue = "uz") AppLanguage lang){
        return ResponseEntity.ok(articleService.getById(id,lang.name()));
    }

    @PutMapping("/delete/{id}")
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<?> delete(@PathVariable String id){
        return ResponseEntity.ok(articleService.delete(id));
    }

    @PutMapping("/change-status/{id}")
    @PreAuthorize("hasRole('PUBLISHER')")
    public ResponseEntity<?> changeStatus(@PathVariable String id,
                                          @RequestHeader(value = "Accepted-Language", defaultValue = "uz") AppLanguage lang){
        return ResponseEntity.ok(articleService.changeStatus(id, lang.name()));
    }

    @GetMapping("/get-last/{id}")
    public ResponseEntity<?> getLastByTypes(@PathVariable Integer id){
        return ResponseEntity.ok(articleService.getArticlesByTypes(id));
    }

    @PostMapping("get-last-ei")
    public ResponseEntity<?> getLastEiByTypes(@RequestBody ArticleIdsDTO dto) {
        return ResponseEntity.ok(articleService.getLast8Articles(dto.getArticleIds()));
    }

    @GetMapping("/get-last4-except")
    public ResponseEntity<?> getLast4Except(@RequestParam String articleId,
                                            @RequestParam(required = false) Integer articleTypeId ) {
        return ResponseEntity.ok(articleService.getLast4ExceptId(articleId,articleTypeId));
    }

    @GetMapping("most-read")
    public ResponseEntity<?> getMostRead(){
        return ResponseEntity.ok(articleService.getMostRead());
    }

    @GetMapping("by-tage")
    public ResponseEntity<?> getByTage(@RequestParam String tage){
        return ResponseEntity.ok(articleService.getByTage(tage));
    }

    @GetMapping("by-region/{regionId}")
    public ResponseEntity<?> getByRegion(@PathVariable String regionId){
        return ResponseEntity.ok(articleService.getByRegion(regionId));
    }

    @GetMapping("by-pag-region")
    public ResponseEntity<?> getByPagRegion(@RequestParam String regionId,
                                            @RequestParam Integer page,
                                            @RequestParam Integer size){
        page = Math.max(page-1, 0);
        return ResponseEntity.ok(articleService.getByPagRegion(regionId, page, size));
    }


    @GetMapping("by-pag-category")
    public ResponseEntity<?> getByPagRegion(@RequestParam Integer categoryId,
                                            @RequestParam Integer page,
                                            @RequestParam Integer size){
        page = Math.max(page-1, 0);
        return ResponseEntity.ok(articleService.getByPagCategory(categoryId, page, size));
    }

    @GetMapping("by-category")
    public ResponseEntity<?> getByPagRegion(@RequestParam Integer categoryId){
        return ResponseEntity.ok(articleService.getByCategory(categoryId));
    }

    @GetMapping("/by-id/{articleId}")
    public ResponseEntity<ArticleShortInfoDTO> byId(@PathVariable String articleId, HttpServletRequest request){
        return ResponseEntity.ok(articleService.byId(articleId, HeaderUtil.getUserIP(request)));
    }

    @PutMapping("/shared/{articleId}")
    public ResponseEntity<Void> sharedCount(@PathVariable String articleId){
        articleService.sharedCount(articleId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/like/{articleId}/like")
    public ResponseEntity<Void> like(@PathVariable String articleId){

        articleService.like(articleId, SpringSecurityUtil.getCurrentUser().getId(), LikeStatus.LIKED);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/like/{articleId}/dislike")

    public ResponseEntity<Void> disLike(@PathVariable String articleId){
        articleService.like(articleId,SpringSecurityUtil.getCurrentUser().getId(), LikeStatus.DISLIKED);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/like/{articleId}/removed")
    public ResponseEntity<Void> removed(@PathVariable String articleId){
        articleService.like(articleId,SpringSecurityUtil.getCurrentUser().getId(), LikeStatus.REMOVED);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }





}
