import type { Meta, StoryObj } from '@storybook/react'
import Register from './Register'
import RegisterLayout from 'src/layouts/RegisterLayout'

const meta = {
  title: 'pages/Register',
  component: Register
} satisfies Meta<typeof Register>

export default meta
type Story = StoryObj<typeof meta>

export const Primary: Story = {
  parameters: {
    reactRouter: {
      routePath: '/register'
    }
  },
  render: () => <Register />
}

export const RegisterPage: Story = {
  parameters: {
    reactRouter: {
      routePath: '/register'
    }
  },
  render: () => <Register />
}

export const RegisterPageWithLayout: Story = {
  parameters: {
    reactRouter: {
      routePath: '/register'
    }
  },
  render: () => (
    <RegisterLayout>
      <Register />
    </RegisterLayout>
  )
}
