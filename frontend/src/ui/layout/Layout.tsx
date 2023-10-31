import Link from 'next/link'
import { FC, ReactNode } from 'react'

const Layout: FC<{ children: ReactNode }> = ({ children }) => {
  return (
    <>
      <header>
        <nav>
          <ul>
            <li>
              <Link href='/'>posts</Link>
            </li>
            <li>
              <Link href='mailto:contact@fakeplastictrees.ee'>contact</Link>
            </li>
          </ul>
        </nav>
      </header>
      <main>{children}</main>
      <footer>
        <Link rel='license' href='/license'>
          license
        </Link>
        <Link
          href='https://codeberg.org/parfentjev/simple-blog'
          target='_blank'
        >
          source
        </Link>
        <Link
          href={
            process.env.NEXT_PUBLIC_SERVICE_URL + `/swagger-ui/index.html#/`
          }
          target='_blank'
        >
          api
        </Link>
      </footer>
    </>
  )
}

export default Layout
