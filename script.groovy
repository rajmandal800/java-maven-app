def buildJar() {
    echo "building the application..."
    sh 'mvn package'
} 

def buildImage() {
    echo "building the docker image..."
    withCredentials([usernamePassword(credentialsId: 'nexus-docker-repo', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
        // sh 'docker build -t nanajanashia/demo-app:jma-2.0 .'
        // sh "echo $PASS | docker login -u $USER --password-stdin"
        // sh 'docker push nanajanashia/demo-app:jma-2.0'

        sh "docker build -t 192.168.64.8:8083/java-maven-app:jma-${params.VERSION} ."
        sh "echo $PASS | docker login -u $USER --password-stdin 192.168.64.8:8083"
        sh "docker push 192.168.64.8:8083/java-maven-app:jma-${params.VERSION}"
    }
} 
def runTests() {
    echo 'running tests...'
}

def deployApp() {
    echo "deploying version: ${params.VERSION} to environment: ${params.ENVIRONMENT}"
    echo 'deploying the application...'
    
    
} 



return this