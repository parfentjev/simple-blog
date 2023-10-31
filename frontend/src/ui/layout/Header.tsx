import { useAuthContext } from '@/store/auth-context'
import Link from 'next/link'
import { FC } from 'react'

const Header: FC<{}> = () => {
  const { token } = useAuthContext()

  return (
    <header>
      <nav>
        <ul>
          <li>
            <Link href='/'>posts</Link>
          </li>
          <li>
            <Link href='/search'>search</Link>
          </li>
          <li>
            <Link href='mailto:contact@fakeplastictrees.ee'>contact</Link>
          </li>
          {token && (
            <li>
              <Link href='/admin'>admin</Link>
            </li>
          )}
        </ul>
      </nav>
    </header>
  )
}

export default Header
