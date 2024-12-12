package cmd

import (
	"fmt"
	"os"
	"os/exec"

	"github.com/spf13/cobra"
)

// startClientCmd represents the startClient command
var startClientCmd = &cobra.Command{
	Use:   "startClient",
	Short: "Start React client dev environment",
	Run: func(cmd *cobra.Command, args []string) {
		frontendCmd := exec.Command("npm", "run", "dev")
		frontendCmd.Dir = "../client" // Path to your frontend folder

		// Capture output
		frontendCmd.Stdout = os.Stdout
		frontendCmd.Stderr = os.Stderr

		// Start frontend
		if err := frontendCmd.Start(); err != nil {
			fmt.Printf("Failed to start frontend server: %v\n", err)
			return
		}
		fmt.Println("Frontend server started asynchronously")

		// Wait for the frontend to finish if needed
		if err := frontendCmd.Wait(); err != nil {
			fmt.Printf("Frontend server exited with error: %v\n", err)
		}
	},
}

func init() {
	rootCmd.AddCommand(startClientCmd)
}
