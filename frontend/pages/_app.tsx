import Layout from '@/ui/layout/Layout'
import { AppProps } from 'next/app'
import './../src/ui/layout/common.css'

const App = ({ Component, pageProps }: AppProps) => {
  return (
    <Layout>
      <Component {...pageProps} />
    </Layout>
  )
}

export default App
