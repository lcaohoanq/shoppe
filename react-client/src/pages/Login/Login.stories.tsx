import type { Meta, StoryObj } from '@storybook/react'
import Login from './Login'
import path from 'src/constants/path'
import RegisterLayout from 'src/layouts/RegisterLayout'

const meta = {
  title: 'pages/Login',
  component: Login
} satisfies Meta<typeof Login>

export default meta
type Story = StoryObj<typeof meta>

export const Primary: Story = {
  parameters: {
    reactRouter: {
      routePath: path.login
    }
  },
  render: () => <Login />
}

export const LoginPage: Story = {
  parameters: {
    reactRouter: {
      routePath: path.login
    }
  },
  render: () => (
    <RegisterLayout>
      <Login />
    </RegisterLayout>
  )
}
