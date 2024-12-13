# PlantTracker

PlantTracker is an Android application designed to help users track and manage their plants effectively. With features like categorizing plants by location, keeping detailed plant care records, and organizing user profiles, this app is perfect for both beginner and experienced plant enthusiasts. This project was created for CST 338: Software Design at CSUMB. 

## Features

- **User Profiles**: Create and manage user profiles to personalize your experience.
- **Plant Database**: Add, update, and delete plants with relevant details like name, type, and watering schedule.
- **Location Tracking**: Assign plants to specific areas (e.g., Living Room, Kitchen) for better organization.
- **Care Reminders**: Record when each plant is watered, and easily view the plants that are due for watering.

## Installation

To run this project on your local machine, follow these steps:

1. Clone this repository:
   ```bash
   git clone https://github.com/danielkarnofel/PlantTracker.git
   ```

2. Open the project in Android Studio.

3. Build the project to ensure all dependencies are resolved.

4. Run the app on an emulator or a connected Android device.

## Technologies Used

- **Java**: Primary programming language for Android development.
- **Android Room**: A persistence library for database management.
- **MVVM Architecture**: Clean and maintainable code structure.
- **Material Design**: For a modern and intuitive user interface.

## Project Structure

```plaintext
src/
├── main/
│   ├── java/
│   │   ├── com.example.planttracker/
│   │   │   ├── models/       # Data entities like User, Plant, and Area
│   │   │   ├── database/     # Room database and DAO classes
│   │   │   ├── viewmodel/    # ViewModels for data handling
│   │   │   ├── ui/           # Activities and Fragments
│   │   │   └── utils/        # Utility classes and helpers
│   ├── res/
│   │   ├── layout/           # XML layouts
│   │   ├── values/           # Strings, themes, and other resources
│   │   └── drawable/         # App icons and images
├── AndroidManifest.xml       # Application configuration
```

## Getting Started

1. Ensure you have the latest version of Android Studio installed.
2. Use the **Device Manager** in Android Studio to create an emulator or connect your Android device.
3. Import the project and run the app.

Feel free to reach out with any questions or suggestions. Happy planting!
