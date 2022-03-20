<script>
  import GetList from '../component/GetList.svelte'
  import { Button, Modal, Col, Row, Container } from 'sveltestrap';
  let isOpen = false;
  const toggle = () => (isOpen = !isOpen);
</script>

<Container>
  <Row>
    <Col>
<h1>Home sweet Home</h1>
 <Button color="primary" on:click={toggle}>Hello World!</Button></Col>
  </Row>



<Row>
    <Col>
<GetList /></Col></Row>
</Container>
<Modal body {isOpen} {toggle} header="Hello World!">
  <p>There's a song that we're singing. Come on</p>
  <img
    src="https://i.ytimg.com/vi/NUJIRujygvY/hqdefault.jpg"
    alt="Come on Get Happy"
    class="img-fluid"
  />
</Modal>
