import { memo } from 'react'
import { Outlet } from 'react-router-dom'
import FooterCompact from 'src/components/FooterCompact'
import Header from 'src/components/Header'
interface Props {
  children?: React.ReactNode
}
function MainLayoutCompactInner({ children }: Props) {
  return (
    <div>
      <Header />
      {children}
      <Outlet />
      <div className='w-full border-t-4 py-2 border-[#f35b04]'></div>
      <FooterCompact />
    </div>
  )
}
const MainLayoutCompact = memo(MainLayoutCompactInner)
export default MainLayoutCompact
