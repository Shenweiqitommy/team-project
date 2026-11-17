# Project Name: Roamly(Temporarily)

## Team Information
We are a team of four University of Toronto students developing Roamly as part of CSC207: Software Design. Our goal is to apply Clean Architecture and software design principles to build a practical, user-friendly travel planning and journaling application.
### Team Members
- Miki Wakabayashi Zheng
- Weiqi(Tommy) Shen
- Andrew Shen
- Jeff Tian

# Travel Planner & Journal

Keep this README current. Update it as part of every PR so the final presentation is easier to prepare.

## Project domain

## Project Domain

-Travel Planning and Journaling.

-Travel Planner & Journal is a Java Swing desktop application designed to help users organize their trips and document their travel experiences in one place. Our goal is to make travel planning more convenient by combining essential planning tools with a personal travel journal.

The application supports users throughout different stages of their journey:

**Travel.** A desktop app (Java Swing) for planning a trip and keeping a journal while on it.

- **Before the trip:** plan the trip (forecast, currency conversion, and more). _Teammates: fill in your features._
- **During the trip:** write a daily journal with photos, take notes, and save memories to revisit later.

**Travel.** A desktop app (Java Swing) for planning a trip and keeping a journal while on it.

- **Before the trip:** plan the trip (forecast, currency conversion, and more). _Teammates: fill in your features._
- **During the trip:** write a daily journal with photos, take notes, and save memories to revisit later.

# Account Management

-User Registration and Login: Create a personal account with a username and password to securely access saved travel information.

-Personalized Experience: Keep trips, itineraries, and journal entries associated with individual accounts.

## User stories

One story per team member.

| User story | Owner |
| --- | --- |
| As a traveller, I want to write a journal entry for each day of my trip and attach up to 3 photos, so I can remember what I did. | Jeff Tian |

| **Weather Forecast:** As a traveler, I want to check the weather forecast for my destination, so that I can prepare for expected weather conditions. | Miki Wakabayashi Zheng |
| **Currency Conversion:** As a traveler, I want to convert Canadian dollars into my destination's local currency, so that I can estimate expenses during my trip. | Miki Wakabayashi Zheng |
| **Saved Trips and Memories:** As a traveler, I want to revisit previously saved trips and journal entries, so that I can look back on my travel experiences. | Miki Wakabayashi Zheng |
| **Packing Progress:** As a traveler, I want to mark checklist items as packed or unpacked, so that I can easily see what I have already prepared. | Miki Wakabayashi Zheng |


| **Trip Creation and Management:** As a traveler, I want to create and manage trips with destinations and travel dates, so that I can organize my travel plans in one place. | Weiqi Shen |
| **Packing Checklist Management:** As a traveler, I want to add and remove items from a packing checklist for each trip, so that I can keep track of everything I need to bring. | Weiqi Shen |
| **Activity Scheduling:** As a traveler, I want to schedule daily activities with locations, start times, and estimated durations, so that I can organize my itinerary efficiently. | Weiqi Shen |


| **Account Registration:** As a traveler, I want to create a personal account using a username and password, so that I can save and manage my travel information. | Andrew Shen |
| **Account Login:** As a traveler, I want to log into my existing account, so that I can securely access my personalized travel information. | Andrew Shen |
| **Personalized Dashboard:** As a traveler, I want to access a dashboard after logging in, so that I can navigate between the different travel planning and journaling features. | Andrew Shen |


## Use cases

### Write daily journal entry (MVP)

**Lead:** Jeff Tian

- **Actor:** Traveller
- **Precondition:** A trip exists.
- **Main flow:**
  1. The user opens the journal and picks a date.
  2. The user writes the entry and optionally attaches up to 3 photos (jpg, jpeg, png, gif).
  3. The user clicks Save.
  4. The system copies the photos into app storage, saves the entry, and confirms with "Saved your entry for <date>."
- **Alternative flows:**
  - The text is empty: show "Write something before saving."
  - The user picks a 4th photo: the Add photo button is disabled; the interactor also rejects more than 3.
  - A file is not a supported image: show "<file> is not a supported image."
  - A photo cannot be copied: show "Could not save photo <file>."
  - The date already has an entry: replace it and show "Updated your entry for <date>."
- **Postcondition:** The entry and its photos are saved and are still there after restarting the app.
- **Not yet done:** checking that the date falls within the trip (needs the Trip entity), and selecting a real trip (uses a placeholder trip id for now).

_Teammates: add your use cases here, marked MVP or later, with the lead's name._

### Use Case 1 — View Weather Forecast
**Owner:** Miki Wakabayashi Zheng

**Related User Story:** Weather Forecast

**Description:** The user can check the weather forecast for their travel destination to prepare for expected weather conditions.

**Main Flow:**
1. The user selects a saved trip.
2. The user clicks the weather forecast option.
3. The system retrieves weather information for the trip's destination using an external weather API.
4. The system displays available weather information, such as temperature, weather conditions, and precipitation.

**Alternative Flow:**
- If the weather information is unavailable or the API request fails, the system displays an appropriate error message.

### Use Case 2 — Convert Currency
**Owner:** Miki Wakabayashi Zheng

**Related User Story:** Currency Conversion

**Description:** The user can convert an amount in Canadian dollars (CAD) into another currency to estimate travel expenses.

**Main Flow:**
1. The user opens the currency converter.
2. The user selects a target currency and enters an amount in CAD.
3. The system retrieves the exchange rate using an external currency API.
4. The system calculates and displays the converted amount.

**Alternative Flow:**
- If the user enters an invalid amount, the system requests a valid input.
- If the exchange rate is unavailable, the system displays an error message.

### Use Case 3 — View Saved Trips and Memories
**Owner:** Miki Wakabayashi Zheng

**Related User Story:** Saved Trips and Memories

**Description:** The user can access previously saved trips and their associated journal entries to revisit travel experiences.

**Main Flow:**
1. The user logs into their account.
2. The user navigates to the saved trips section.
3. The system retrieves and displays the trips associated with the user's account.
4. The user selects a trip.
5. The system displays the saved trip details and associated journal entries.

**Alternative Flow:**
- If the user has no saved trips, the system displays an appropriate message.
- If a selected trip contains no journal entries, the system displays the trip information with an empty journal section.

### Use Case 4 — Update Packing Progress
**Owner:** Miki Wakabayashi Zheng

**Related User Story:** Packing Progress

**Description:** The user can mark packing checklist items as packed or unpacked to track their preparation progress.

**Main Flow:**
1. The user selects a trip and opens its packing checklist.
2. The system displays the checklist and the current packing status of each item.
3. The user marks an item as packed or unpacked.
4. The system updates and saves the item's packing status.
5. The updated checklist is displayed.

**Alternative Flow:**
- If the packing checklist is empty, the system displays a message indicating that no items have been added yet.


### Use Case 5 — Create and Manage Trips
**Owner:** Weiqi Shen

**Related User Story:** Trip Creation and Management

**Description:** The user can create and manage trips by specifying destinations and travel dates, allowing them to organize their travel plans in one place.

**Main Flow:**
1. The user logs into their account.
2. The user selects the option to create a new trip.
3. The user enters a destination, start date, and end date.
4. The system validates the provided information.
5. The system creates and saves the trip under the user's account.
6. The new trip is displayed in the user's trip list.

**Alternative Flow:**
- If the destination or travel dates are missing, the system prompts the user to complete the required fields.
- If the end date is earlier than the start date, the system displays an error message.
- If the user has no existing trips, the system displays an empty trip list with an option to create a new trip.

### Use Case 6 — Manage Packing Checklist
**Owner:** Weiqi Shen

**Related User Story:** Packing Checklist Management

**Description:** The user can add and remove packing items associated with a specific trip to organize what they need to bring.

**Main Flow:**
1. The user selects an existing trip.
2. The user opens the packing checklist for that trip.
3. The system displays the existing packing items.
4. The user enters the name of a new item and clicks "Add".
5. The system adds and saves the item to the trip's packing checklist.
6. The user can select an existing item and remove it from the checklist.
7. The system updates and displays the checklist.

**Alternative Flow:**
- If the user attempts to add an item with an empty name, the system displays an error message.
- If the checklist has no items, the system displays an empty checklist.
- If the selected item no longer exists, the system displays an appropriate error message.

### Use Case 7 — Schedule Travel Activities
**Owner:** Weiqi Shen

**Related User Story:** Activity Scheduling

**Description:** The user can schedule activities for a trip by specifying locations, dates, start times, and estimated durations to organize their daily itinerary.

**Main Flow:**
1. The user selects an existing trip.
2. The user opens the activity planner.
3. The user enters an activity name, location, date, start time, and estimated duration.
4. The system validates the information and checks that the activity falls within the trip dates.
5. The system saves the scheduled activity to the selected trip.
6. The system displays the activity in the itinerary in chronological order.

**Alternative Flow:**
- If required information is missing or invalid, the system displays an error message.
- If the scheduled date falls outside the trip's travel dates, the system asks the user to select a valid date.
- If the new activity overlaps with an existing activity, the system warns the user about the scheduling conflict.

### Use Case 8 — Account Registration (MVP)

**Lead:** Andrew Shen

**Related User Story:** Account Registration

**Actor:** Traveler

**Precondition:** The user is not logged into an existing account.

**Description:** The user can create a personal account to store and access their travel information.

**Main Flow:**
1. The user opens the application and selects the registration option.
2. The user enters a username, password, and password confirmation.
3. The system validates the provided information.
4. The system checks whether the username already exists.
5. The system creates a new user account and securely stores the account information.
6. The system confirms successful registration and redirects the user to the login screen.

**Alternative Flow:**
- If any required fields are empty, the system prompts the user to complete them.
- If the username already exists, the system requests a different username.
- If the passwords do not match, the system displays an error message.
- If the account cannot be saved, the system displays an appropriate error message.

**Postcondition:** A new user account is created and can be used to log into the application.

### Use Case 9 — Account Login (MVP)

**Lead:** Andrew Shen

**Related User Story:** Account Login

**Actor:** Traveler

**Precondition:** The user has an existing registered account.

**Description:** The user can log into their personal account to access their saved travel information and application features.

**Main Flow:**
1. The user opens the login screen.
2. The user enters their username and password.
3. The system retrieves the corresponding account information.
4. The system verifies the provided credentials.
5. The system identifies the authenticated user and establishes the current user session.
6. The system redirects the user to their personalized dashboard.

**Alternative Flow:**
- If the username or password is empty, the system prompts the user to complete the required fields.
- If the username or password is incorrect, the system displays an error message.
- If the account information cannot be retrieved, the system displays an appropriate error message.

**Postcondition:** The user is authenticated and can access the travel information associated with their account.

### Use Case 10 — Access Personalized Dashboard (MVP)

**Lead:** Andrew Shen

**Related User Story:** Personalized Dashboard

**Actor:** Traveler

**Precondition:** The user has successfully logged into their account.

**Description:** The user can navigate between the application's travel planning and journaling features through a personalized dashboard.

**Main Flow:**
1. The user successfully logs into their account.
2. The system displays the main dashboard associated with the authenticated user.
3. The dashboard provides navigation options for trip management, saved trips, journal entries, weather forecasts, and currency conversion.
4. The user selects their desired feature.
5. The system opens the corresponding view while maintaining the current user's session.

**Alternative Flow:**
- If the user logs out, the system clears the current session and returns to the login screen.
- If the user attempts to access a feature without authentication, the system redirects them to the login screen.

**Postcondition:** The user can navigate between application features and access information associated with their account.


## Entities

- **JournalEntry:** `tripId`, `date`, `text`, `photos` (at most 3). Methods: `addPhoto`, `removePhoto`, `canAddPhoto`, `setText` (rejects blank text).
- **Photo:** `filePath`.
- _To be added: Trip and the other entities._

- **User:** `userId`, `username`, `passwordHash`. Methods: `getUserId`, `getUsername`.

- **Trip:** `tripId`, `userId`, `destination`, `startDate`, `endDate`. Methods: `setDestination`, `updateTravelDates`, `isWithinTripDates`.

- **Activity:** `activityId`, `tripId`, `name`, `location`, `date`, `startTime`, `duration`. Methods: `updateActivity`, `getEndTime`, `overlapsWith`.

- **PackingItem:** `itemId`, `tripId`, `name`, `isPacked`. Methods: `setName`, `markAsPacked`, `markAsUnpacked`, `isPacked`.

- **JournalEntry:** `tripId`, `date`, `text`, `photos` (at most 3). Methods: `addPhoto`, `removePhoto`, `canAddPhoto`, `setText` (rejects blank text).

- **Photo:** `filePath`. Methods: `getFilePath`.

These entities will be shared across different use cases and implemented following Clean Architecture principles.

---

## API Usage

Our application plans to integrate three external APIs to provide map visualization, weather forecasts, and currency conversion.

### 1. Geoapify Static Maps API

**Documentation:** https://apidocs.geoapify.com/docs/maps/static/

**How it is used in the program:**

The Geoapify Static Maps API will be used to display maps of travel destinations and planned activity locations. Users will be able to view their destinations and important locations through a map integrated into the Java Swing interface.

**Planned Features:**
- Display a map of the selected trip destination.
- Mark planned activity locations on the map.
- Provide geographical context for travel planning.

**Integration Status:** Planned — Not yet tested.

### 2. Open-Meteo Weather API

**Documentation:** https://open-meteo.com/en/docs

**How it is used in the program:**

The Open-Meteo API will provide weather forecasts for selected travel destinations. The application will retrieve available weather data using the destination's geographical coordinates and display information to help users prepare for their trips.

**Planned Features:**
- Display destination weather forecasts.
- Show expected temperatures and weather conditions.
- Provide precipitation information when available.

**Integration Status:** Planned — Not yet tested.

### 3. Frankfurter Currency Exchange API

**Documentation:** https://frankfurter.dev/

**How it is used in the program:**

The Frankfurter API will retrieve currency exchange rates to help users estimate expenses while traveling. Users will be able to enter an amount in Canadian dollars (CAD), select a target currency, and view the converted amount.

**Planned Features:**
- Retrieve available exchange rates.
- Convert CAD into supported foreign currencies.
- Display calculated conversion results.

**Integration Status:** Planned — Not yet tested.

- **User:** Represents a registered user of the application.
  - Instance variables: `userId`, `username`, `passwordHash`.
  - Methods: `getUserId()`, `getUsername()`, `getPasswordHash()`.
 

## API usage

_To be added: link to each API's documentation, how the project uses it, and whether the team has called it successfully._

## Persistence

The application uses JSON-based local storage to save and retrieve travel-related information.

- **Trips:** Trip details, including destinations and travel dates, are stored in `data/trips.json`.
- **Activities:** Scheduled activities are stored in `data/activities.json`.
- **Packing Checklists:** Packing items and their completion status are stored in `data/packing.json`.
- **Travel Journals:** Journal entries are stored in `data/journal.json`.
- **Photos:** Attached photos are copied into `data/photos/` and referenced by their corresponding journal entries.
- **User Accounts:** User registration and login information will be managed through a separate authentication storage system.

Each user's travel information will be associated with their unique user ID to ensure that users can only access their own saved data.

The `data/` directory is created automatically at runtime and is excluded from Git version control.

## Running the Project

### Prerequisites
- Java Development Kit (JDK) 11 or newer
- Apache Maven

### Running the Application

```bash
mvn exec:java
```

### Running Unit Tests

```bash
mvn test
```

The application uses Java Swing for its graphical user interface and follows Clean Architecture principles to separate business logic, interface adapters, and external services.

## Screenshots

**User Accounts:** Registered user accounts are stored in `data/users.json`, containing unique user IDs, usernames, and securely hashed passwords.

**User Session:** The application maintains the currently authenticated user's ID during runtime. The session is cleared when the user logs out, and user-specific travel information is retrieved using the corresponding user ID.

  
_Add screenshots or animations as features land._
