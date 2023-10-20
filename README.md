# MealMate

MealMate is an Android application built in Kotlin that assists users in meal preparation. It leverages the [TheMealDB](https://www.themealdb.com/api.php/) web service and the Room Library to manage meal information. This project allows users to add, search, and explore meals, making meal planning and preparation easier.

## Features

1. **Add Meals to DB**: Save meal details to a local SQLite database using the Room library.
2. **Search for Meals By Ingredient**: Search for meals based on a specific ingredient from TheMealDB web service. You can also save these meals to the local database.
3. **Search for Meals**: Search for meals in the local database based on a keyword (case-insensitive and partial matching supported).
4. **Image Display**: View meal images (thumbnails) when searching for meals.
5. **User-Friendly Rotation**: The application seamlessly handles device rotation without losing user data or screen position.
6. **Quick Search**: Search for meals directly from the web service based on a partial name match.

## Screenshots

<div style="display: flex; flex-wrap: wrap; justify-content: center; margin-bottom: 40px;">
  <img src="https://github.com/Himidiri/MealMate/assets/102814081/cf266acf-a17b-40d9-b688-cc24e9e41940" alt="Screenshot 1" style="width: 400px; height: auto; margin-right: 20px; margin-bottom: 40px;"/>
  <img src="https://github.com/Himidiri/MealMate/assets/102814081/1ca4375e-3219-4b8f-85f7-03d4e6afb096" alt="Screenshot 2" style="width: 400px; height: auto; margin-left: 20px; margin-bottom: 40px;"/>
</div>

<div style="display: flex; flex-wrap: wrap; justify-content: center; margin-bottom: 40px;">
  <img src="https://github.com/Himidiri/MealMate/assets/102814081/96aac35d-045a-42d5-8951-4940907c17de" alt="Screenshot 1" style="width: 400px; height: auto; margin-right: 20px; margin-bottom: 40px;"/>
  <img src="https://github.com/Himidiri/MealMate/assets/102814081/1c4687ba-3620-4b90-96d0-f0199295451e" alt="Screenshot 2" style="width: 400px; height: auto; margin-left: 20px; margin-bottom: 40px;"/>
</div>

<div style="display: flex; flex-wrap: wrap; justify-content: center; margin-bottom: 40px;">
  <img src="https://github.com/Himidiri/MealMate/assets/102814081/e2298691-e0f4-4bce-ba02-9a6d0467a985" alt="Screenshot 3" style="width: 800px; margin-bottom: 40px;"/>
</div>

<div style="display: flex; flex-wrap: wrap; justify-content: center;">
  <img src="https://github.com/Himidiri/MealMate/assets/102814081/a602255c-f668-41e2-b75c-2a649cc1787f" alt="Screenshot 4" style="width: 800px;"/>
</div>

<div style="display: flex; flex-wrap: wrap; justify-content: center;">
  <img src="https://github.com/Himidiri/MealMate/assets/102814081/355902ac-457e-4cad-8a5c-c66ca0f942bb" alt="Screenshot 4" style="width: 800px;"/>
</div>

## Getting Started

### Prerequisites

- Android Studio
- Kotlin
- Internet connection for web service functionality

### Installation

1. Clone the repository:

   ```bash
   git clone https://github.com/Himidiri/MealMate.git
