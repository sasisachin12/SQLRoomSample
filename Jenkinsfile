pipeline {
    agent any

    environment {
        ANDROID_HOME = "/opt/android-sdk"
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
