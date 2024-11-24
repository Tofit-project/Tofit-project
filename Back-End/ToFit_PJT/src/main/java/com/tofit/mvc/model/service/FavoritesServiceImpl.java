package com.tofit.mvc.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tofit.mvc.model.dao.FavoritesDao;
import com.tofit.mvc.model.dto.Favorites;
import com.tofit.mvc.model.dto.FavoritesView;

@Service
public class FavoritesServiceImpl implements FavoritesService{

   private final FavoritesDao favoritesDao;
   
   public FavoritesServiceImpl(FavoritesDao favoritesDao) {
      this.favoritesDao = favoritesDao;
   }

   // 찜 조회
   @Override
   public Favorites getFav(Favorites fav) {
      return favoritesDao.selectFav(fav);
   }
   
   // 찜 등록
   @Override
   public boolean doFav(Favorites fav) {
      int result = favoritesDao.insertFav(fav);
      return result == 1;
   }

   // 찜 해제
   @Override
   public boolean undoFav(Favorites fav) {
      int result = favoritesDao.deleteFav(fav);
      return result == 1;
   }

@Override
public List<FavoritesView> getList(String userId) {
   return favoritesDao.selectList(userId);
}
   
}
