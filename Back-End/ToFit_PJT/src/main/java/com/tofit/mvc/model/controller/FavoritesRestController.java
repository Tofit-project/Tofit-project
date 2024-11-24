package com.tofit.mvc.model.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tofit.mvc.jwt.JwtUtil;
import com.tofit.mvc.model.dto.Favorites;
import com.tofit.mvc.model.dto.FavoritesView;
import com.tofit.mvc.model.service.FavoritesService;

@RestController
@RequestMapping("/tofit/video")
@CrossOrigin("*")
public class FavoritesRestController {

   private final FavoritesService favoritesService;
   private final JwtUtil jwtUtil;
   
   public FavoritesRestController(FavoritesService favoritesService, JwtUtil jwtUtil) {
      this.favoritesService = favoritesService;
      this.jwtUtil = jwtUtil;
   }
   
   @GetMapping("/favoriteList")
   public ResponseEntity<?> list(@RequestHeader(value = "Authorization") String token){
      token = token.replace("Bearer ", "");
      String userId = jwtUtil.getUserIdFromToken(token);
      
      List<FavoritesView> list = favoritesService.getList(userId);
      
      if(list == null || list.size() == 0)
         return new ResponseEntity<String>("찜 목록이 없습니다", HttpStatus.NO_CONTENT);
      
      return new ResponseEntity<List<FavoritesView>>(list, HttpStatus.OK);
   }

   // 찜 조회
   @GetMapping("/{videoId}/favorite")
   public ResponseEntity<Boolean> detail(@PathVariable("videoId") String videoId, @RequestHeader(value = "Authorization") String token){
      token = token.replace("Bearer ", "");
      String userId = jwtUtil.getUserIdFromToken(token);

      Favorites fav = new Favorites();
      fav.setUserId(userId);
      fav.setVideoId(videoId);
      
      Favorites info = favoritesService.getFav(fav);
      
      if(info == null)
         return new ResponseEntity<Boolean>(false, HttpStatus.OK);
      
      return new ResponseEntity<Boolean>(true, HttpStatus.OK);
   }
   
   // 찜 등록
   @PostMapping("/{videoId}/favorite")
   public ResponseEntity<?> create(@PathVariable("videoId") String videoId, @RequestHeader(value = "Authorization") String token){
      token = token.replace("Bearer ", "");
      String userId = jwtUtil.getUserIdFromToken(token);
      
      Favorites fav = new Favorites();
      fav.setUserId(userId);
      fav.setVideoId(videoId);
      
      boolean isOk = favoritesService.doFav(fav);
      
      if(isOk)
         return new ResponseEntity<String>("등록 완료", HttpStatus.CREATED);
      return new ResponseEntity<String>("등록 불가", HttpStatus.INTERNAL_SERVER_ERROR);
   }
   
   // 찜 삭제
   @DeleteMapping("/{videoId}/favorite")
   public ResponseEntity<?> remove(@PathVariable("videoId") String videoId, @RequestHeader(value = "Authorization") String token){
      token = token.replace("Bearer ", "");
      String userId = jwtUtil.getUserIdFromToken(token);
      
      Favorites fav = new Favorites();
      fav.setUserId(userId);
      fav.setVideoId(videoId);
      
      boolean isOk = favoritesService.undoFav(fav);
      
      if(isOk)
         return new ResponseEntity<String>("해제 완료", HttpStatus.ACCEPTED);
      return new ResponseEntity<String>("해제 불가", HttpStatus.SERVICE_UNAVAILABLE);
   }
   
}
