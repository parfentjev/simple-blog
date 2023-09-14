import Link from 'next/link'
import { FC, ReactNode } from 'react'
import './common.css'

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
      <main>
        {/* {navigation.state === 'loading' && <p>Loading...</p>} */}
        {children}
      </main>
      <footer>
        <Link
          rel='license'
          href='http://creativecommons.org/licenses/by/4.0/'
          target='_blank'
        >
          license
        </Link>
        <Link
          href={process.env.REACT_APP_SERVICE_URL + `/swagger-ui/index.html#/`}
          target='_blank'
        >
          api
        </Link>
      </footer>
    </>
  )
}

export default Layout
