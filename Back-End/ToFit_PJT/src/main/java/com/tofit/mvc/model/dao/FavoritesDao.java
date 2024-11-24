package com.tofit.mvc.model.dao;

import java.util.List;

import com.tofit.mvc.model.dto.Favorites;
import com.tofit.mvc.model.dto.FavoritesView;

public interface FavoritesDao {

   int insertFav(Favorites fav);

   int deleteFav(Favorites fav);

   Favorites selectFav(Favorites fav);

   List<FavoritesView> selectList(String userId);

}
