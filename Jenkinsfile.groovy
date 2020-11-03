properties([

	parameters ([
		string (defaultValue: '', description: 'Enter the Docker Image Name', name: 'Image_name', trim: false),
	])
])


pipeline {
	agent {label 'master'}
	
	stages {
		stage('CLEAN WORKSPACE IN JENKINS SERVER'){
			steps {
				cleanWs()
			} // Steps Completed	
		}  // Stage Completed
	

		stage('CLONE THE SOURCE CODE FROM GIT-HUB'){
			steps {
				echo 'In SCM Stage'
				
				git credentialsId: 'f278eddc-9430-40ea-8dd6-048a31ddca83', url: 'https://github.com/gopinathPersonal/dockerTest.git',branch: 'main'
				sh '''
					ls
					pwd
				'''

			} // Steps Completed
		}  // Stage Completed	


		stage('Restarting Docker Service Status'){
			steps {
				
				sh'''
					systemctl status docker
					systemctl restart docker
				'''
			} // Steps Completed
		}  // Stage Completed

		stage('Verifiying the Dockerfile'){
			steps {
				
				sh'''
					echo 'Docker file verification'
					
					pwd
					ls
					cat Dockerfile
				'''
			} // Steps Completed
		}  // Stage Completed


		stage('Docker Build Image'){
			steps {
				
				sh'''
					echo 'Build Image'
					
					docker build -t $Image_name .
				'''
			} // Steps Completed
		}  // Stage Completed

		stage('Image Creation Status'){
			steps {
				
				sh'''
									
					docker images
				'''
			} // Steps Completed
		}  // Stage Completed
	}
}
