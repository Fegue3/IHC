# MindSpace - Mental Health Manager

MindSpace is a comprehensive **Mental Health Management Application** developed using **JavaFX**. It allows users to log their emotions, explore mental health activities (such as breathing exercises, meditation, and relaxing sounds), and visualize personal statistics over time. All user data is securely stored in **MongoDB Cloud**.

## 🌟 Features

* User registration and login (authentication)
* Emotion logging and journal entries
* Guided breathing exercises with animations
* Meditation tips and techniques
* Ambient relaxing sound player with volume and progress control
* Daily calendar to view and organize mental health records
* Automatic statistical charts and summaries based on user emotions

---

## 🧠 Project Motivation

MindSpace aims to offer a **user-friendly and interactive space** where individuals can monitor and improve their mental wellbeing. Through consistent tracking and access to tools for relaxation and self-reflection, the app helps users better understand their emotional health.

---

## 🛠️ Technologies Used

| Technology       | Purpose                                 |
| ---------------- | --------------------------------------- |
| JavaFX           | Graphical User Interface (GUI)          |
| Scene Builder    | Visual design of FXML views             |
| MongoDB Atlas    | Cloud database for storing user data    |
| Gson             | JSON parsing for persistent data        |
| JFoenix          | Material Design components in JavaFX    |
| MVC Architecture | Maintainable and modular code structure |

---

## 🧱 Project Structure (MVC)

MindSpace follows the **MVC pattern**, separating logic (Controllers), structure (Model), and layout (View via FXML).

```
├── model/             # Data structures and MongoDB access (DAOs)
├── controller/        # All UI logic and user interactions
├── view/              # FXML layouts created with Scene Builder
├── utils/             # Utility classes (if needed)
├── config/            # Properties files for config and user preferences
├── style/             # Application CSS styling
├── resources/         # Images, sounds, other media
├── tests/             # Data seeding and unit testing
└── Main.java          # Application entry point
```

---

## 🧭 Views and Their Purpose

### 🔐 Login & Register

* **Login.fxml**, **RegisterLogin.fxml**, **RegisterView\.fxml**
* Controllers: `LoginController.java`, `RegisterLoginController.java`
* Authenticates users and stores new accounts in MongoDB.
* Screenshot:
<img width="400" height="619" alt="login" src="https://github.com/user-attachments/assets/2ce5d08e-f65c-4ac9-b06d-06c47d9d473d" />


### 🏠 Main Menu

* **MainMenu.fxml**
* Controller: `MainMenuController.java`
* Central navigation hub to access all sections.
* Screenshot:
<img width="397" height="664" alt="mainmenu" src="https://github.com/user-attachments/assets/cbb09866-0196-4509-878e-067569229702" />


### 📊 Statistics

* **Stats.fxml**
* Controller: `StatsController.java`
* Displays mood trends, averages, activity logs using charts.
* Screenshot:
<img width="553" height="752" alt="statsº" src="https://github.com/user-attachments/assets/e53e5025-ab53-4183-b92c-bcee8a8a9d26" />


### 📅 Calendar & Journal

* **History.fxml**
* Controller: `HistoryController.java`
* Lists daily mood logs and personal notes.

### 🧘 Meditation

* **Meditacao.fxml**
* Controller: `MeditacaoController.java`
* Shows meditation tips and mindfulness quotes.

### 🌬️ Breathing Exercises

* **BreathingEx.fxml**
* Controller: `BreathingExController.java`
* Animated breathing guidance with timing.

### 🎵 Relaxing Sounds

* **RelaxingSounds.fxml**
* Controller: `RelaxingSoundsController.java`
* Ambient sound player with volume and track controls.

### ❤️ Wellness Section

* **Saude.fxml**
* Controller: `SaudeController.java`
* Offers curated resources for emotional wellness.

---

## ☁️ Database Design (MongoDB)

* Each user has a unique `userId`.
* Collections:

  * `users`: stores user credentials and session info
  * `registos`: stores mood logs, emotional notes, and points
  * `dicas`: stores meditation tips and motivational suggestions

Example Document (in `entries`):

```json
{
  "_id": "ObjectId('684ca78de4fd492b82f6869c')",
  "userId": "684ca050ab03733283064364",
  "data": "13/06/2025",
  "emotion": "Radiante",
  "note": "ABC",
  "points": 6
}
```

---

## ⚙️ Configuration Files

* `config.properties`: stores default application settings.
* `user.properties`: persists current user session and login data.

---

## 🧪 Testing

* **Seeder.java** in `mindspace.tests`
* Used to populate MongoDB with fake users and emotional data for testing and demo purposes.

---

## 🎨 Styling

* Located in `mindspace.style/mindspace.css`
* Defines colors, fonts, layout tweaks for all views.

---

## 🚀 Getting Started

1. **Clone the repository**

```bash
git clone https://github.com/Fegue3/MindSpace.git
```

2. **Open in your IDE** (NetBeans, IntelliJ, or Eclipse with JavaFX support)
3. **Install dependencies** (Scene Builder, MongoDB Atlas credentials)
4. **Run Main.java**

---

## 📌 Future Improvements

* Custom emotion icons
* Push notifications & reminders
* Integration with health wearables (e.g. smartwatch)
* Localization (multi-language support)

---

## 👥 Authors

* **Fegue3** ([GitHub](https://github.com/Fegue3))

---

## 📬 Feedback

Feel free to open issues or contact the developer directly through GitHub if you'd like to contribute or suggest new features!
