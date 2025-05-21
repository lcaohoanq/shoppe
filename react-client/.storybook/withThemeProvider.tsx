import React, { useEffect } from 'react'
import { Decorator } from '@storybook/react'
import { ThemeProvider, THEME_CHANGE_EVENT, ThemeMode } from '../src/themes/ThemeProvider'
import '../src/index.css'

// This decorator wraps stories with our ThemeProvider
export const withThemeProvider: Decorator = (Story, context) => {
  const ThemeWrapper: React.FC = () => {
    // Get the current theme from Storybook context
    const { globals } = context
    const currentTheme = globals.theme || 'light'

    useEffect(() => {
      // Listen for theme changes from our app's ThemeToggle
      const handleThemeChange = (event: Event) => {
        const customEvent = event as CustomEvent<{ mode: ThemeMode }>
        if (customEvent.detail?.mode) {
          // Use Storybook's API to update the global theme
          const newTheme = customEvent.detail.mode
          if (newTheme !== currentTheme) {
            // This triggers Storybook to update its UI
            context.setGlobals({ theme: newTheme })
          }
        }
      }

      window.addEventListener(THEME_CHANGE_EVENT, handleThemeChange)
      return () => {
        window.removeEventListener(THEME_CHANGE_EVENT, handleThemeChange)
      }
    }, [currentTheme, context])

    return (
      <ThemeProvider>
        <Story />
      </ThemeProvider>
    )
  }

  return <ThemeWrapper />
}
