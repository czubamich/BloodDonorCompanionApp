# Blood Donor Companion App
<!-- PROJECT SHIELDS -->
[![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
[![License][license-shield]][license-url]

<!-- PROJECT LOGO -->
<br />
  <p align="left">
    Blood Donor Companion App is an app designed for active blood donors that want to track their donation history. </br>
    Application is based on polish blood donation rules.
</p>

<!-- TABLE OF CONTENTS -->
<details open="open">
  <summary><h2 style="display: inline-block">Table of Contents</h2></summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
        <li><a href="#features">Features</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
        <li><a href="#report-issues">Report Issues</a></li>
      </ul>
    </li>
    <li><a href="#contact">Contact</a></li>
    <li><a href="#acknowledgements">Acknowledgements</a></li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->
## About The Project

[![Product Screen Shot][screenshot.png]](https://github.com/czubamich/BloodDonorCompanionApp/blob/master/screenshot.png)

### Built With

* [Kotlin](https://kotlinlang.org/)
* [MVVM](https://developer.android.com/jetpack/docs/guide)
* [Room](https://developer.android.com/topic/libraries/architecture/room) - Access your app's SQLite database with in-app objects and compile-time checks.
* [Compose](https://developer.android.com/jetpack/compose) - Modern toolkit for building native UI.
* [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) - Provide sophisticated tools to handle concurrency.
* [Lifecycles](https://developer.android.com/topic/libraries/architecture/lifecycle) - Create a UI that automatically responds to lifecycle events.
* [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - Build data objects that notify views when the underlying database changes.
* [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Store UI-related data that isn't destroyed on app rotations. Easily schedule asynchronous tasks.
* [Material Design](https://material.io/develop/android) - Material is a design system created by Google to help teams build high-quality digital experiences for Android, iOS, Flutter, and the web.

### Features
* Adding/removing donations
* Welcome screen for new users
* Support for donating whole bloods, platelets and plasma
* Profile summary
* Adding/removing donations
* Automatic scheduling for next donation based on donation types

<!-- GETTING STARTED -->
## Getting Started

To get a local copy up and running follow these simple steps.

### Prerequisites
To build this project, you require:

* Android Studio 4.1.1
* Gradle 7.0
* Android Device (Minimum API 26)


### Installation
1. Clone the repo
   ```sh
   git clone https://github.com/czubamich/BloodDonorCompanionApp.git
   ```
2. Open project in Android Studio.
3. Run 'app' SHIFT+F10.

### Report issues
Something not working quite as expected? Do you need a feature that has not been implemented yet? Check the issue tracker and add a new one if your problem is not already listed. Please try to provide a detailed description of your problem, including the steps to reproduce it.

<!-- CONTACT -->
## Contact

Michael Czuba - czuba.mich@gmail.com

<!-- ACKNOWLEDGEMENTS -->
## Acknowledgements

* [LiveEvent](https://github.com/hadilq/LiveEvent)
* [SmoothBottomBar](https://github.com/ibrahimsn98/SmoothBottomBar)

<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[contributors-shield]: https://img.shields.io/github/contributors/czubamich/BloodDonorCompanionApp.svg?style=for-the-badge
[contributors-url]: https://github.com/czubamich/BloodDonorCompanionApp/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/czubamich/BloodDonorCompanionApp.svg?style=for-the-badge
[forks-url]: https://github.com/czubamich/BloodDonorCompanionApp/network/members
[stars-shield]: https://img.shields.io/github/stars/czubamich/BloodDonorCompanionApp.svg?style=for-the-badge
[stars-url]: https://github.com/czubamich/BloodDonorCompanionApp/stargazers
[issues-shield]: https://img.shields.io/github/issues/czubamich/BloodDonorCompanionApp.svg?style=for-the-badge
[issues-url]: https://github.com/czubamich/BloodDonorCompanionApp/issues
[license-shield]: https://img.shields.io/github/license/czubamich/BloodDonorCompanionApp.svg?style=for-the-badge
[license-url]: https://github.com/czubamich/BloodDonorCompanionApp/blob/master/LICENSE.txt

<!-- README created using the following template -->
<!-- https://github.com/othneildrew/Best-README-Template -->