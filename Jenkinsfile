pipeline {
    agent any

    environment {
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
                sh "echo \"sdk.dir=$ANDROID_HOME\" > local.properties"
            }
        }

        stage('Lint') {
            steps {
                sh './gradlew lint'
            }
        }

        stage('Build APKs') {
            steps {
                // Building both at once to ensure they exist
                sh './gradlew assembleDebug assembleRelease'

                // Debug: List files to see exactly where APKs are
                echo "Listing generated APKs..."
                sh 'find . -name "*.apk"'
            }
        }
    }

    post {
        success {
            echo 'Build Successful! Archiving APKs...'
            // Using a broader glob pattern to ensure artifacts are found
            archiveArtifacts artifacts: '**/*.apk', allowEmptyArchive: false
        }
        failure {
            echo 'Build Failed. Please check the logs above for Gradle errors.'
        }
        cleanup {
            echo 'Cleaning up workspace...'
            cleanWs()
        }
    }
}
