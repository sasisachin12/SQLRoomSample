pipeline {
    agent any

    environment {
        // You can set these globally in Jenkins, but defining them here ensures they are available
        // Note: Change these paths to match your Jenkins Node's actual locations
        ANDROID_HOME = "${env.ANDROID_HOME ?: '/opt/android-sdk'}"
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Setup Environment') {
            steps {
                sh 'chmod +x gradlew'
                // Create local.properties dynamically if it's missing to satisfy Gradle
                sh "echo \"sdk.dir=$ANDROID_HOME\" > local.properties"
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
            echo 'Pipeline finished.'
            cleanWs()
        }
    }
}
