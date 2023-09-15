import Layout from '@/ui/Layout'
import { AppProps } from 'next/app'
import './../src/ui/common.css'

const App = ({ Component, pageProps }: AppProps) => {
  return (
    <Layout>
      <Component {...pageProps} />
    </Layout>
  )
}

export default App
