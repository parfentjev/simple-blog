import { useAuthContext } from '@/store/auth-context'
import Link from 'next/link'
import { FC, useCallback, useState } from 'react'
import Button, { ButtonStyle } from './element/Button'

const Header: FC<{}> = () => {
  const { token } = useAuthContext()
  const [isMenuVisible, setMenuVisible] = useState(false)

  const handleShowMenuButton = useCallback(() => {
    setMenuVisible((state) => !state)
  }, [])

  const handleLinkClick = useCallback(() => {
    setMenuVisible(false)
  }, [])

  return (
    <header>
      <nav>
        <Button
          text='show menu'
          style={ButtonStyle.BorderOnly}
          onClick={handleShowMenuButton}
        />
        <ul className={(isMenuVisible && 'navbar_visible') || undefined}>
          <li>
            <Link href='/' onClick={handleLinkClick}>
              posts
            </Link>
          </li>
          <li>
            <Link href='/categories' onClick={handleLinkClick}>
              categories
            </Link>
          </li>
          <li>
            <Link href='/search' onClick={handleLinkClick}>
              search
            </Link>
          </li>
          <li>
            <Link href='/static/feed.xml' onClick={handleLinkClick}>
              rss
            </Link>
          </li>
          {token && (
            <li>
              <Link href='/admin' onClick={handleLinkClick}>
                admin
              </Link>
            </li>
          )}
        </ul>
      </nav>
    </header>
  )
}

export default Header
