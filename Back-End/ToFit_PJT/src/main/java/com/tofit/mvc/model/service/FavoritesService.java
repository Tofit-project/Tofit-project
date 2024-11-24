package com.tofit.mvc.model.service;

import java.util.List;

import com.tofit.mvc.model.dto.Favorites;
import com.tofit.mvc.model.dto.FavoritesView;

public interface FavoritesService {

   boolean doFav(Favorites fav);

   boolean undoFav(Favorites fav);

   Favorites getFav(Favorites fav);

   List<FavoritesView> getList(String userId);

}