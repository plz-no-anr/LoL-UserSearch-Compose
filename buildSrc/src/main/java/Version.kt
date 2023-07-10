object Version {

    object Plugin {
        const val gradle = "8.0.2"
        const val kotlin = "1.8.21"
        const val hilt = "2.45"
    }

    object AndroidX {
        const val coreKtx = "1.10.0"
        const val appCompat = "1.6.1"
        const val lifecycle = "2.6.1"
        const val navigation = "2.6.0"
        const val navigationCompose = "1.0.0"
        const val room = "2.5.2"
        const val activity = "1.7.2"
        const val splash = "1.0.0"
        const val dataStore = "1.0.0"
        const val compose = "androidx.compose:compose-bom:2023.04.00"
    }

    object ThirdParty {
        const val material = "1.8.0"
        const val coroutine = "1.7.1"
        const val retrofit = "2.9.0"
        const val viewPager = "0.20.1"
        const val accompanist = "0.28.0"
        const val kotlinSerialization = "1.5.0"
        const val kotlinSerializationConverter = "0.8.0"
        const val timber = "5.0.1"

        object Compose {
            const val coil = "2.3.0"
            const val lottie = "5.2.0"
        }
    }

    object UnitTest {
        const val junit = "4.13.2"
        const val roboelectric = "4.6.1"
        const val mockk = "1.12.4"
        const val okhttp3MockWebServer = "4.9.2"
    }

    object AndroidTest {
        const val junit = "1.1.3"
        const val espresso = "3.4.0"
    }

}