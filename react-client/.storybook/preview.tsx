import '../src/index.css'
import React from 'react'
import { HelmetProvider } from 'react-helmet-async'
import ErrorBoundary from '../src/components/ErrorBoundary'
import { QueryClient, QueryClientProvider } from '@tanstack/react-query'
import { AppProvider } from '../src/contexts/app.context'
import { withThemeByDataAttribute } from '@storybook/addon-themes'
import { withThemeProvider } from './withThemeProvider'
import { withRouter } from 'storybook-addon-remix-react-router'

export const parameters = {
  actions: { argTypesRegex: '^on[A-Z].*' },
  controls: {
    matchers: {
      color: /(background|color)$/i,
      date: /Date$/
    }
  },
  themes: {
    default: 'light',
    list: [
      {
        name: 'Light',
        class: 'light-theme',
        color: '#ffffff',
        default: true
      },
      {
        name: 'Dark',
        class: 'dark-theme',
        color: '#000000'
      }
    ]
  },

  decorators: [
    // Our custom decorator that provides the ThemeProvider
    withThemeProvider,
    // Storybook's theme decorator (keep this)
    withThemeByDataAttribute({
      themes: {
        light: 'light-theme',
        dark: 'dark-theme'
      },
      defaultTheme: 'light',
      attributeName: 'class'
    })
  ],

  // Make the theme available as a global variable
  globals: {
    theme: 'light'
  },

  tags: ['autodocs']
}

const queryClient = new QueryClient({
  defaultOptions: {
    queries: {
      retry: false
    },
    mutations: {
      retry: false
    }
  },
  logger: {
    log: console.log,
    warn: console.warn,
    // no more errors on the console
    error: () => null
  }
})

export const decorators = [
  withRouter,
  (Story) => (
    <QueryClientProvider client={queryClient}>
      <AppProvider>
        <HelmetProvider>
          <ErrorBoundary>
            <Story />
          </ErrorBoundary>
        </HelmetProvider>
      </AppProvider>
    </QueryClientProvider>
  )
]
