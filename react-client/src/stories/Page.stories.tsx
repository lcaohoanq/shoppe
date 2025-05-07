import type { Meta, StoryObj } from '@storybook/react'
import { Page } from './Page'
import userEvent from '@testing-library/user-event'
import { within } from '@testing-library/react'

const meta = {
  title: 'Example/Page',
  component: Page,
  parameters: {
    layout: 'fullscreen'
  }
} satisfies Meta<typeof Page>

export default meta

type Story = StoryObj<typeof meta>

export const LoggedOut: Story = {}

export const LoggedIn: Story = {
  play: async ({ canvasElement }) => {
    const canvas = within(canvasElement)
    const loginButton = await canvas.getByRole('button', { name: /Log in/i })
    await userEvent.click(loginButton)
  }
}
