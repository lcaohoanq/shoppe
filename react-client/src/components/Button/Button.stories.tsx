import type { Meta, StoryObj } from '@storybook/react'
import Button from './Button'

const meta = {
  title: 'Components/Button',
  component: Button,
  argTypes: {
    isLoading: {
      description: 'Hiển thị icon loading'
    },
    children: {
      description: 'Nội dung button',
      table: {
        type: { summary: 'React.ReactNode' },
        defaultValue: { summary: '' }
      }
    },
    className: {
      description: 'class',
      table: {
        type: { summary: 'string' },
        defaultValue: { summary: '' }
      }
    }
  }
} satisfies Meta<typeof Button>

export default meta

type Story = StoryObj<typeof meta>

export const Primary: Story = {
  args: {
    children: 'Đăng nhập',
    className:
      'flex w-full items-center justify-center bg-red-500 py-4 px-2 text-sm uppercase text-white hover:bg-red-600',
    isLoading: true
  }
}

export const Secondary: Story = {
  args: {
    children: 'Đăng ký',
    className:
      'inline-flex items-center justify-center bg-red-500 py-4 px-2 text-sm uppercase text-white hover:bg-red-600',
    isLoading: true,
    disabled: true
  }
}
