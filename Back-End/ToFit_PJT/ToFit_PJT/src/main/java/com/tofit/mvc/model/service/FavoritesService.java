package com.tofit.mvc.model.service;

import com.tofit.mvc.model.dto.Favorites;

public interface FavoritesService {

	boolean doFav(Favorites fav);

	boolean undoFav(Favorites fav);

	Favorites getFav(Favorites fav);


}
