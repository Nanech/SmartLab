# **SmartLab**

*SmartLab* - информационная система для частных поликлиник.
Используя данное приложение можно как заказывать различные тесты и анализы, так и увидеть  все результаты ранее сделанных тестов или анализов в одном приложении.

*Является хорошим примером для построения своих android приложений с использованием "XML layouts to build native UI".* То есть более старой реализации android приложений.

## Как скачать данное решение

Gradle build файл содержит в себе следующий код:

``` build.gradle.kts
 compileSdk = 34

    defaultConfig {
        applicationId = "com.example.smartlab"
        minSdk = 30
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
```

Это означает, что разработанное приложение может быть запущено на устройствах Android 11 и выше.

Чтобы установить данный проект, **вам нужно иметь [Android Studio](https://developer.android.com/studio) на вашем устройстве**, при запуске проекта необходимо убедиться, что у вас установлен 30 API или выше**.

**Также иногда стоит синхронизировать gradle.kts файлы и запустить AGP инспекцию, если у вас версия более ранее или поздняя чем "Android Studio Giraffe".**

## Что реализовано в данном проекте

### Встречающие пользователя элементы

Элементы встречающие пользователя "Splash Screen" и "OnBoarding/Walkthrough Screen".

* *Splash Screen* - всплывающий экран заставки, реализован через специальное [API](https://developer.android.com/develop/ui/views/launch/splash-screen), которое ввели начиная с Android 12.
* *OnBoarding/Walkthrough Screen* - экран первоначальной загрузки, который запускается только в том случае, если вход в приложение является первым.

**В ходе реализации нужно было придумать *изюминку* здесь** это - [LottieAnimation](https://lottiefiles.com/). Это анимации, которые могут быть представлены как в "RAW", так и в "JSON-OBJECT" формате, они могут быть использованы в браузере и мобильных приложениях. Некоторые анимации являются полностью бесплатными, что даёт полное право использовать их в своих решениях (как здесь).

<p align="center">
  <img alt="Splash & OnBoarding" src="/app/gitresources/splash_and_onboarding.gif">
</p>

### Навигационные элементы

 В контексте данного решения был использован навигационный компонент с использованием множества фрагментов.

<p align="center">
  <img alt="Навигация" src="/app/gitresources/navigation.png">
</p>

Между некоторыми аргумента передаются аргументы. К примеру, с "AuthFragment" в "EmailCodeFragment" передаётся почта, чтобы отправлять разные запросы к API.

### Список реализованного

Здесь все то, что присутствует в проекте (BL - Business Logic):

* Bindings (Activity & Fragment)
* API Models
* Validation of code from email
* RegEx
* Bottom Navigation

+ Fragments
  + Auth with BL
  + EmailCode with BL
  + OnBoarding with BL
  + ProfileCardCreate with BL
  + PassCode Creation with BL

- Preference Manager
  - Walkthrough Screens
  - Pass Code
  - JWT Token

* API manipulations
  * Send Code to email
  * Take JWT
  * Create (post) Profile Card

-----

## Что не реализовано

Не хватило времени на реализацию всех окон внутри Bottom Navigation (all in figma). То есть нету: 

* Обновления данных личного кабинета;
* Сохранение фотографии внутри личного кабинета, как и нет картинки заглушки;
* Вытягивание из API:
  * Акций и новостей;
  * Анализов;
  * Данных для Modal Bottom Sheet;
* Корзины и её бизнес логики;
* Оформление заказа.

## Ссылки

Макет приложения - [Figma](https://www.figma.com/file/cuibB4VoSTob0Jjt33ICPV/CopySL?type=design&node-id=2%3A2197&mode=design&t=isFMpAx2hxBMIvT8-1)

GitHub profile - [Nanech](https://github.com/Nanech)
 
[Gogs repository](http://gogs.ngknn.ru:3000/NikitaAgeev/SmartLab)

[API](https://iis.ngknn.ru/NGKNN/%D0%9C%D0%B0%D0%BC%D1%88%D0%B5%D0%B2%D0%B0%D0%AE%D0%A1/MedicMadlab/)