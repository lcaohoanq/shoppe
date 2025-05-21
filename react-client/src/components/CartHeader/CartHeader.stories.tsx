import type { Meta, StoryObj } from '@storybook/react'
import CartHeader from './CartHeader'

const meta = {
  title: 'Components/CartHeader',
  component: CartHeader
} satisfies Meta<typeof CartHeader>

export default meta
type Story = StoryObj<typeof meta>

export const Primary: Story = {
  render: () => <CartHeader />
}
