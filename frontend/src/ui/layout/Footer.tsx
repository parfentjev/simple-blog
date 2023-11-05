import Link from 'next/link'
import { FC } from 'react'

const Footer: FC<{}> = () => {
  return (
    <footer>
      <Link href='mailto:contact@fakeplastictrees.ee'>contact</Link>
      <Link rel='license' href='/license'>
        license
      </Link>
      <Link href='https://codeberg.org/parfentjev/simple-blog' target='_blank'>
        source
      </Link>
      <Link
        href={process.env.NEXT_PUBLIC_SERVICE_URL + `/swagger-ui/index.html#/`}
        target='_blank'
      >
        api
      </Link>
    </footer>
  )
}

export default Footer
