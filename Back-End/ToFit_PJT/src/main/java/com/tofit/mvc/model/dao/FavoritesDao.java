package com.tofit.mvc.model.dao;

import com.tofit.mvc.model.dto.Favorites;

public interface FavoritesDao {

   int insertFav(Favorites fav);

   int deleteFav(Favorites fav);

   Favorites selectFav(Favorites fav);

}
