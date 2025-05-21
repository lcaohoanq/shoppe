import type { Meta, StoryObj } from '@storybook/react'
import MainLayout from './MainLayout'
import ProductDetail from 'src/pages/ProductDetail'
import { ReactKeycloakProvider } from '@react-keycloak/web'
import { keycloakMock } from '../../../.storybook/keycloakMock'
const meta = {
  title: 'Layouts/MainLayout',
  component: MainLayout,
  decorators: [
    (Story) => (
      <ReactKeycloakProvider authClient={keycloakMock}>
        <Story />
      </ReactKeycloakProvider>
    )
  ]
} satisfies Meta<typeof MainLayout>

export default meta
type Story = StoryObj<typeof meta>

export const PageProductDetail: Story = {
  render: () => (
    <MainLayout>
      <ProductDetail />
    </MainLayout>
  ),
  parameters: {
    reactRouter: {
      routePath: '/:nameId',
      routeParams: {
        nameId: 'Dien-thoai-OPPO-A12-3GB32GB--Hang-chinh-hang-i-60afb2426ef5b902180aacb9'
      }
    }
  }
}
