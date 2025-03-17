# MoniePoint Shipping Tracker

MoniePoint Shipping Tracker is an Android Jetpack Compose application that helps users:

- View active shipments and shipping details (Home Screen).  
- Calculate shipping costs based on sender/receiver location, weight, and item categories.  
- Search for shipments by receipt number.  
- Track shipment history by status (All, Completed, In Progress, Pending, Cancelled).  
- Get an estimated shipping amount with a quick, animated cost display.

---

## Table of Contents

1. [Key Features](#key-features)  
2. [Screenshots](#screenshots)  
3. [Demo Video](#demo-video)  
4. [APK Download](#apk-download)  
5. [How to Build & Run](#how-to-build--run)  
6. [Project Structure](#project-structure)  
7. [Libraries & Tech Stack](#libraries--tech-stack)  
8. [License](#license)

---

## Key Features

1. **Home Screen**  
   - Displays a bottom navigation bar.  
   - Shows **Tracking** cards and a list of **Available Vehicles** in a horizontal slider.  
   - “Search” bar leading to a detailed Search Screen.

2. **Search Screen**  
   - Search for shipments by receipt number.  
   - Displays filtered results in a LazyColumn with item details.

3. **Calculate Shipment Screen**  
   - Allows users to input sender and receiver location, approximate weight.  
   - Choose packaging type and category via selectable chips.  
   - Provides a **Calculate** button to generate shipping cost.

4. **Shipment History**  
   - Utilizes tabs or a ViewPager for **All**, **Completed**, **In Progress**, **Pending**, **Cancelled**.  
   - Displays shipments in a stylized card with status, arrival time, order number, receiving location, price, and date.

5. **Estimated Amount**  
   - Shows a final calculation with an **animated** total cost.  
   - Option to return to Home Screen.

---

## Screenshots

| Home Screen                                       | Calculate Shipment                                 |
|---------------------------------------------------|----------------------------------------------------|
| <img src="https://github.com/odogwudev/MoniePointShippingTracker/blob/266d082602eb63a364c399c8edd2bbeae9594881/home_screen.png" width="300"> | <img src="https://github.com/odogwudev/MoniePointShippingTracker/blob/266d082602eb63a364c399c8edd2bbeae9594881/calculate_shipment.png" width="300"> |

| Estimate Amount                                   | Search Screen                                      |
|---------------------------------------------------|----------------------------------------------------|
| <img src="https://github.com/odogwudev/MoniePointShippingTracker/blob/266d082602eb63a364c399c8edd2bbeae9594881/estimate_amount.png" width="300"> | <img src="https://github.com/odogwudev/MoniePointShippingTracker/blob/266d082602eb63a364c399c8edd2bbeae9594881/search_screen.png" width="300"> |

| Shipment History                                  |
|---------------------------------------------------|
| <img src="https://github.com/odogwudev/MoniePointShippingTracker/blob/266d082602eb63a364c399c8edd2bbeae9594881/shipment_history.png" width="300"> |

---

## Demo Video

[**Click here**](https://github.com/odogwudev/MoniePointShippingTracker/blob/14368639eadacdf86c01ee00895d24749788a474/monierecord.mp4) to watch a short demo of the MoniePoint Shipping Tracker in action.

---

## APK Download

You can install or test the app by downloading the APK file directly:

[**demo.apk**](https://github.com/odogwudev/MoniePointShippingTracker/blob/7984ef703b4ff579f583b23328a6ac370f5816de/demo.apk)

---

## How to Build & Run

1. **Clone** this repository:
   ```bash
   git clone https://github.com/odogwudev/MoniePointShippingTracker.git
``
2.	**Open the project** in Android Studio (version Bumblebee or higher recommended).
3.	**Sync Gradle** to download all required dependencies.
4.	**Run/Build** on an emulator or physical device.

## Project Structure

A simplified look at the high-level package structure:


MoniePointShippingTracker
└── app
    ├── MainActivity.kt
    ├── navigation
    │    ├── NavGraph.kt
    │    └── NavRoutes.kt
    ├── ui
    │    ├── home
    │    │    └── HomeScreen.kt
    │    ├── shipment
    │    │    └── CalculateShipmentScreen.kt
    │    ├── history
    │    │    └── ShipmentHistoryScreen.kt
    │    ├── search
    │    │    └── SearchScreen.kt
    │    └── estimated
    │         └── EstimatedAmountScreen.kt
    ├── models
    │    └── ShippingItem.kt
    ├── theme
    │    └── Color.kt
    │    └── Theme.kt
    └── utils
         └── ...


	•	HomeScreen: Displays tracking items and available vehicles.
	•	CalculateShipmentScreen: Input form for calculating shipment costs.
	•	ShipmentHistoryScreen: View shipments by status.
	•	SearchScreen: Filter shipments by receipt number.
	•	EstimatedAmountScreen: Shows animated total cost and “Back to Home” button.



