# Dish Recipes

![Made by](https://img.shields.io/badge/Made%20by-r--khvstnv-orange)
![API](https://img.shields.io/badge/API-21%2B-brightgreen)
![GitHub top language](https://img.shields.io/github/languages/top/r-khvstnv/DishRecipes)
![Lines of code](https://img.shields.io/tokei/lines/github/r-khvstnv/DishRecipes)
![GitHub issues](https://img.shields.io/github/issues/r-khvstnv/DishRecipes)
<br/>
![Dagger2](https://img.shields.io/badge/Dagger2-2.41-blue)
![Retrofit2](https://img.shields.io/badge/Retrofit2-2.9.0-green)
![RxJava](https://img.shields.io/badge/RxJava-3.0.0-red)
![Room](https://img.shields.io/badge/Room-2.42-brightgreen)
![Lifecycle](https://img.shields.io/badge/Lifecycle-2.4.1-brightgreen)
![Navigation](https://img.shields.io/badge/Navigation-2.4.2-brightgreen)

![image](i_previews/logo_dish_recipes_long.png)

_____

## Introductions
Dish Recipes is application where you can store favorite recipes and find new ideas for cooking. Applications is based on modern technologies, such as: MVVM Architecture, Dependency Injection with MultiBinding, Jetpack.
<br/><br/>

## Overview
### All Dishes
All Dishes is a Fragment, where User can see all the recipes ever added. It means that, all dishes created by User and received by Api Call, firstly get there. Furthermore, User can choose how Recipes are displayed, Edit or Delete them and Filter them by Type and Category. Filter list is dynamic, so it will be based on the existing data in Local Repository.
<br/>
![image](i_previews/all_linear_no_toolbar.png)
![image](i_previews/all_grid.png)
![image](i_previews/all_linear_filter.png)
<br/><br/>

### Favorite Dishes
_(1st Screenshot below)_ Fragment provides all favorite recipes. Like in the previous fragment, there is available selection of List displaying, Dish Editing and Deleting.
<br/>
![image](i_previews/fav_grid.png)
![image](i_previews/add_update_first.png)
![image](i_previews/add_update_second.png)
<br/><br/>

### Add & Update Dish
_(2nd & 3rd Screenshot above)_ Fragment, where User can add New Dish. In addition it's used for Editing Existed.
<br/><br/>

### Dish Details
Fragment, provides all related information to previously chosen Recipe. There, User can add it to Favorite or Delete. 
<br/>
__NOTE:__ Only created by User Dishes are available for Editing _(1st & 2nd Screenshot below)_
<br/>
![image](i_previews/details_api.png)
![image](i_previews/details_owner.png)
![image](i_previews/random_api.png)
<br/><br/>

### Random Dish
Fragment request Api call and provides received Random Recipe to User. Dish automatically saves to local repository. However, recipe Source Link is only available in this fragment and will not be saved _(3rd Screenshot above)_

___

## Technology Stack
- Kotlin
- Jetpack
	- Lifecycle
	- LiveData
	- ViewModel
	- WorkManager
	- Room
	- Navigation Component & safeArgs
- Dagger2
	 - Multibinding
- Retrofit2
- RxJava3
- Glide
