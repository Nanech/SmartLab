# **SmartLab**

*SmartLab* - информационная система для частных поликлиник.
Используя данное приложение можно заказывать различные тесты и анализы, а также можно увидеть результаты своих анализов в одном приложении.

## Что реализовано в данном проекте

Приложение встречает пользователя "Splash Screen" и "OnBoarding/Walkthrough Screen".

* *Splash Screen* - всплывающий экран заставки, реализован через специальное [API](https://developer.android.com/develop/ui/views/launch/splash-screen), которое ввели начиная с Android 12.
* OnBoarding/Walkthrough Screen - экран первоначальной загрузки, который запускается только в том случае, если вход в приложение является первым.

```build.gradle.kts
implementation("androidx.core:core-splashscreen:1.0.1") // Splash
```

<p align="center">
  <img alt="Splash & OnBoarding" src="/app/gitresources/splash_and_onboarding.gif">
</p>


 В контексте данного решения был использован навигационный компонент с использованием множества фрагментов.

<p align="center">
  <img alt="Навигация" src="/app/gitresources/navigation.png">
</p>

* Splash Screen
* OnBoarding Screens
* Bindings
* API Models
* Navigation of Fragments
* Animations into OnBoarding
* Validation of code from email
* API connection

* RegEx
  * Auth Fragment
  * Card Creation
* Fragments
  * Auth with BL
  * EmailCode with BL
  * OnBoarding with BL
  * ProfileCardCreate with BL
  * PassCode Creation with BL

* Preference Manager
  * Walkthrough Screens
  * Pass Code
  * JWT Token taker

* API manipulations
  * Send Code to email
  * Take JWT
  * Create (post) Profile Card

-----

## Что необходимо реализовать далее

* Another Fragments
* Make Bottom Navigate Activity after it make few Fragments with some logic (Scroll Frag, Modal Buttom Sheet)
* Make some logic with Fragments
