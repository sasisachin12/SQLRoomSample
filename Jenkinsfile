pipeline {
    agent any

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
        post {
                always {
                    echo 'Pipeline finished.'
                    cleanWs()
                }
            }
    }
}
