# WeatherTell

WeatherTell is an Android application developed as a coursework assignment for the Mobile Platform Development course (MHI324189-23-B). The app leverages RSS feeds from the BBC Weather to provide current weather conditions and a 3-day forecast for various global locations. It's designed with the latest mobile UI/UX principles using Android Studio and Java/XML.

## Features
- **Current Weather Display**: Shows the current weather conditions for selected cities.
- **3-Day Weather Forecast**: Users can view brief and detailed 3-day weather forecast for each selected location.
- **Navigation**: Swipe through weather details for different cities using **_ViewPager2_**. There is also a bottom nav bar for moving through screens.
- **User Interface**: Provides for both landscape and portrait modes.

## Code Components

### Architecture
- Uses the Model-View-ViewModel (MVVM) architecture for efficient data management and UI state control.

### Key Components
- **Models**: Includes CurrentWeather and WeatherForecast for parsing and managing weather data.
- **ViewModels**: Handles data fetching and lifecycle-aware data presentation.
- **Views**: Activities with corresponding XML layouts for displaying data.
- **Utilities**: Includes network utilities and XML parsers for fetching and processing data from the BBC Weather RSS feeds.
