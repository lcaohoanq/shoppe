import { ReactNode, createContext, useContext, useEffect, useMemo, useState } from 'react'
import { ThemeProvider as MuiThemeProvider, createTheme } from '@mui/material/styles'
import CssBaseline from '@mui/material/CssBaseline'

// Define the theme mode type
export type ThemeMode = 'light' | 'dark'

// Define the event name for theme changes
export const THEME_CHANGE_EVENT = 'theme-mode-change'

// Create a context for the theme
interface ThemeContextType {
  mode: ThemeMode
  toggleTheme: () => void
  setMode: (mode: ThemeMode) => void
}

const ThemeContext = createContext<ThemeContextType>({
  mode: 'light',
  toggleTheme: () => {},
  setMode: () => {}
})

// Hook to use the theme context
export const useThemeContext = () => useContext(ThemeContext)

interface Props {
  children: ReactNode
}

export const ThemeProvider = ({ children }: Props) => {
  // Get initial theme from local storage or default to light
  const [mode, setModeState] = useState<ThemeMode>(() => {
    // Check if we're in Storybook
    const isStorybook = window.location.href.includes('storybook')

    if (isStorybook) {
      // In Storybook, try to get theme from URL or localStorage
      const urlParams = new URLSearchParams(window.location.search)
      const themeParam = urlParams.get('theme')
      if (themeParam === 'dark' || themeParam === 'light') {
        return themeParam
      }
    }

    // Otherwise use localStorage
    const savedMode = localStorage.getItem('theme-mode')
    return (savedMode === 'dark' ? 'dark' : 'light') as ThemeMode
  })

  // Set mode function with event dispatch
  const setMode = (newMode: ThemeMode) => {
    setModeState(newMode)

    // Dispatch a custom event that Storybook can listen to
    window.dispatchEvent(new CustomEvent(THEME_CHANGE_EVENT, { detail: { mode: newMode } }))

    // Save to localStorage
    localStorage.setItem('theme-mode', newMode)
  }

  // Toggle theme function
  const toggleTheme = () => {
    const newMode = mode === 'light' ? 'dark' : 'light'
    setMode(newMode)
  }

  // Create theme context value
  const themeContextValue = useMemo(
    () => ({
      mode,
      toggleTheme,
      setMode
    }),
    [mode]
  )

  // Update body class when theme changes
  useEffect(() => {
    const body = document.body

    // Remove previous theme classes
    body.classList.remove('light-theme', 'dark-theme')

    // Add current theme class
    body.classList.add(`${mode}-theme`)
  }, [mode])

  // Listen for theme changes from Storybook
  useEffect(() => {
    const handleStorybookThemeChange = (event: any) => {
      if (event.type === 'change' && event.target?.dataset?.selectedTheme) {
        const newTheme = event.target.dataset.selectedTheme
        if (newTheme === 'light' || newTheme === 'dark') {
          // Only update if different to avoid loops
          if (newTheme !== mode) {
            setModeState(newTheme)
          }
        }
      }
    }

    // Try to find Storybook theme buttons and monitor them
    const storybookThemeButtons = document.querySelectorAll('[data-selected-theme]')
    storybookThemeButtons.forEach((btn) => {
      btn.addEventListener('change', handleStorybookThemeChange)
    })

    return () => {
      storybookThemeButtons.forEach((btn) => {
        btn.removeEventListener('change', handleStorybookThemeChange)
      })
    }
  }, [mode])

  // Define the MUI theme
  const theme = useMemo(
    () =>
      createTheme({
        palette: {
          mode,
          ...(mode === 'light'
            ? {
                // Light mode palette
                primary: {
                  main: '#EE4D2D'
                },
                secondary: {
                  main: '#03a9f4'
                },
                background: {
                  default: '#f5f5f5',
                  paper: '#ffffff'
                },
                text: {
                  primary: '#000000',
                  secondary: '#666666'
                },
                divider: '#e0e0e0'
              }
            : {
                // Dark mode palette
                primary: {
                  main: '#FF6347' // Slightly lighter shade in dark mode
                },
                secondary: {
                  main: '#29b6f6'
                },
                background: {
                  default: '#121212',
                  paper: '#1e1e1e'
                },
                text: {
                  primary: '#ffffff',
                  secondary: '#aaaaaa'
                },
                divider: '#333333'
              })
        },
        components: {
          MuiPaper: {
            styleOverrides: {
              root: {
                backgroundColor: mode === 'light' ? '#ffffff' : '#1e1e1e',
                backgroundImage: 'none'
              }
            }
          },
          MuiButton: {
            styleOverrides: {
              root: {
                borderRadius: 8
              }
            }
          },
          MuiTextField: {
            styleOverrides: {
              root: {
                '& .MuiOutlinedInput-root': {
                  '& fieldset': {
                    borderColor: mode === 'light' ? '#e0e0e0' : '#333333'
                  }
                }
              }
            }
          }
        }
      }),
    [mode]
  )

  return (
    <ThemeContext.Provider value={themeContextValue}>
      <MuiThemeProvider theme={theme}>
        <CssBaseline />
        {children}
      </MuiThemeProvider>
    </ThemeContext.Provider>
  )
}
