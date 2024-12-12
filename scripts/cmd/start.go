package cmd

import (
	"fmt"
	"github.com/spf13/cobra"
	"log"
	"os"
	"os/exec"
	"sync"
)

// startCmd represents the start command
var startCmd = &cobra.Command{
	Use:   "start",
	Short: "Start Docker containers, backend, and frontend servers asynchronously",
	Run: func(cmd *cobra.Command, args []string) {
		fmt.Println("Starting Docker containers...")

		// Step 1: Start Docker containers
		if err := startDockerContainers(); err != nil {
			log.Fatalf("Failed to start Docker containers: %v", err)
		}

		fmt.Println("Docker containers started successfully")

		// Step 2: Start backend and frontend servers asynchronously
		fmt.Println("Starting backend and frontend servers asynchronously...")

		// Create a WaitGroup to wait for both goroutines to finish
		var wg sync.WaitGroup
		wg.Add(2)

		// Create channels to handle success and errors
		backendChan := make(chan error, 1)
		frontendChan := make(chan error, 1)

		// Start backend server in a goroutine
		go startBackendServer(backendChan, &wg)

		// Start frontend server in a goroutine
		go startFrontendServer(frontendChan, &wg)

		// Wait for both servers to start
		wg.Wait()

		// Check if there were any errors
		if err := <-backendChan; err != nil {
			log.Fatalf("Backend server error: %v", err)
		}
		if err := <-frontendChan; err != nil {
			log.Fatalf("Frontend server error: %v", err)
		}

		fmt.Println("Both backend and frontend servers started successfully.")
	},
}

func init() {
	rootCmd.AddCommand(startCmd)
}

// startDockerContainers starts Docker containers using docker-compose
func startDockerContainers() error {
	// Command to start Docker containers
	dockerCmd := exec.Command("docker", "compose", "-f", "docker-compose-dev.yml", "up", "-d")
	dockerCmd.Dir = "../SPCServer/springboot/docker" // Path to your springboot folder

	// Capture output
	dockerCmd.Stdout = os.Stdout
	dockerCmd.Stderr = os.Stderr

	// Start Docker containers
	if err := dockerCmd.Run(); err != nil {
		return fmt.Errorf("failed to start Docker containers: %v", err)
	}

	return nil
}

// startBackendServer starts the backend server and sends any error to the channel
func startBackendServer(ch chan<- error, wg *sync.WaitGroup) {
	defer wg.Done()

	// Command to start the backend server (in the springboot folder)
	backendCmd := exec.Command("mvn", "spring-boot:run")
	backendCmd.Dir = "../SPCServer/springboot" // Path to your backend folder

	// Capture output
	backendCmd.Stdout = os.Stdout
	backendCmd.Stderr = os.Stderr

	// Start backend
	if err := backendCmd.Start(); err != nil {
		ch <- fmt.Errorf("failed to start backend server: %v", err)
		return
	}
	fmt.Println("Backend server started asynchronously")

	// Wait for the backend to finish if needed
	err := backendCmd.Wait()
	ch <- err
}

// startFrontendServer starts the frontend server and sends any error to the channel
func startFrontendServer(ch chan<- error, wg *sync.WaitGroup) {
	defer wg.Done()

	// Command to start the frontend server (in the client folder)
	frontendCmd := exec.Command("npm", "run", "dev")
	frontendCmd.Dir = "../client" // Path to your frontend folder

	// Capture output
	frontendCmd.Stdout = os.Stdout
	frontendCmd.Stderr = os.Stderr

	// Start frontend
	if err := frontendCmd.Start(); err != nil {
		ch <- fmt.Errorf("failed to start frontend server: %v", err)
		return
	}
	fmt.Println("Frontend server started asynchronously")

	// Wait for the frontend to finish if needed
	err := frontendCmd.Wait()
	ch <- err
}
