pipeline {
    agent any

    environment {
        ANDROID_HOME = "/opt/android-sdk" // Adjust this to your Jenkins node's SDK path
        JAVA_HOME = "${tool 'JAVA_17_HOME'}" // Adjust 'JAVA_17_HOME' to your Jenkins Tool name
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Set Permissions') {
            steps {
                sh 'chmod +x gradlew'
            }
        }

        stage('Lint and Static Analysis') {
            steps {
                sh './gradlew lint'
            }
        }

        stage('Unit Tests') {
            steps {
                sh './gradlew testDebugUnitTest'
            }
        }

        stage('Build Debug APK') {
            steps {
                sh './gradlew assembleDebug'
            }
        }

        stage('Build Release APK') {
            steps {
                // Note: For release builds, you usually need signing configs set up in build.gradle
                // or passed via environment variables.
                sh './gradlew assembleRelease'
            }
        }
    }

    post {
        success {
            echo 'Build Successful! Archiving APKs...'
            archiveArtifacts artifacts: 'app/build/outputs/apk/**/*.apk', followSymlinks: false
        }
        failure {
            echo 'Build Failed. Checking logs...'
        }
        always {
            steps {
                echo 'Cleaning up workspace...'
                // deleteDir()
            }
        }
    }
}
