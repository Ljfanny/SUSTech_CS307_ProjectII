<template>
  <div>
    <el-container>
      <el-header> </el-header>
      <el-header> CS307 ProjectII </el-header>
      <el-header>
        <el-row>
          <el-col :span="2">
            <el-button type="primary" plain @click="backToHome">Home</el-button>
          </el-col>
          <el-col :span="2">
            <el-button type="primary" plain @click="getAll">Inventory</el-button>
          </el-col>
          <el-col :span="2">
            <el-button type="primary" plain @click="get">select</el-button>
          </el-col>
          <el-col :span="2">
            <el-button type="primary" plain @click="dlt">delete</el-button>
          </el-col>
          <el-col :span="2">
            <el-button type="primary" plain @click="insert">insert</el-button>
          </el-col>
          <el-col :span="2">
            <el-button type="primary" plain @click="update">update</el-button>
          </el-col>
        </el-row>
      </el-header>
      <el-header>
        <el-row>
          <el-col :span="2">
            <el-input v-model="iuid" placeholder="id"></el-input>
          </el-col>
          <el-col :span="0.1">
            &#160;
          </el-col>
          <el-col :span="2">
            <el-input v-model="iusc" placeholder="sup center"></el-input>
          </el-col>
          <el-col :span="0.1">
            &#160;
          </el-col>
          <el-col :span="2">
            <el-input v-model="iupm" placeholder="pro model"></el-input>
          </el-col>
          <el-col :span="0.1">
            &#160;
          </el-col>
          <el-col :span="2">
            <el-input v-model="iuss" placeholder="sup staff"></el-input>
          </el-col>
          <el-col :span="0.1">
            &#160;
          </el-col>
          <el-col :span="2">
            <el-input v-model="iudate" placeholder="date"></el-input>
          </el-col>
          <el-col :span="0.1">
            &#160;
          </el-col>
          <el-col :span="2">
            <el-input v-model="iupp" placeholder="pur price"></el-input>
          </el-col>
          <el-col :span="0.1">
            &#160;
          </el-col>
          <el-col :span="2">
            <el-input v-model="iusq" placeholder="sur quantity"></el-input>
          </el-col>
          <el-col :span="0.1">
            &#160;
          </el-col>
          <el-col :span="2">
            <el-input v-model="iutq" placeholder="tot quantity"></el-input>
          </el-col>
        </el-row>
      </el-header>
    </el-container>
    <el-table :data="tableData" stripe border style="width: 100%" height="500">
      <el-table-column fixed prop="id" label="id" width="150">
      </el-table-column>
      <el-table-column prop="supplyCenter" label="supply center" width="150"> </el-table-column>
      <el-table-column prop="productModel" label="product model" width="150"> </el-table-column>
      <el-table-column prop="supplyStaff" label="supply staff" width="150">
      </el-table-column>
      <el-table-column prop="date" label="date" width="150">
      </el-table-column>
      <el-table-column prop="purchasePrice" label="purchase price" width="150">
      </el-table-column>
      <el-table-column prop="surplusQuantity" label="surplus quantity" width="150">
      </el-table-column>
      <el-table-column prop="totalQuantity" label="total quantity" width="150"> </el-table-column>
    </el-table>
  </div>
</template>

<script>
export default {
  data () {
    return {
      tableData: [],
      iuid: '',
      iusc: '',
      iupm: '',
      iuss: '',
      iudate: '',
      iupp: '',
      iusq: '',
      iutq: ''
    }
  },
  methods: {
    backToHome () {
      this.$router.push('Login')
    },
    getAll () {
      this.tableData = []
      this.$axios
        .get('/inventories/all')
        .then((response) => { this.tableData = response.data.data })
    },
    get () {
      this.tableData = []
      this.$axios
        .get('/inventories?' +
        'id=' + this.iuid +
        '&supplyCenter=' + this.iusc +
        '&productModel=' + this.iupm +
        '&supplyStaff=' + this.iuss +
        '&date=' + this.iudate +
        '&purchasePrice=' + this.iupp +
        '&surplusQuantity=' + this.iusq +
        '&totalQuantity=' + this.iutq)
        .then((response) => { this.tableData = response.data.data })
      this.iuid = ''
      this.iusc = ''
      this.iupm = ''
      this.iuss = ''
      this.iudate = ''
      this.iupp = ''
      this.iusq = ''
      this.iutq = ''
    },
    dlt () {
      this.tableData = []
      this.$axios.delete('/inventories?' +
        'id=' + this.iuid +
        '&supplyCenter=' + this.iusc +
        '&productModel=' + this.iupm +
        '&supplyStaff=' + this.iuss +
        '&date=' + this.iudate +
        '&purchasePrice=' + this.iupp +
        '&surplusQuantity=' + this.iusq +
        '&totalQuantity=' + this.iutq)
      this.iuid = ''
      this.iusc = ''
      this.iupm = ''
      this.iuss = ''
      this.iudate = ''
      this.iupp = ''
      this.iusq = ''
      this.iutq = ''
    },
    insert () {
      this.$axios
        .post('/inventories', {
          id: this.iuid,
          supplyCenter: this.iusc,
          productModel: this.iupm,
          supplyStaff: this.iuss,
          date: this.iudate,
          purchasePrice: this.iupp,
          surplusQuantity: this.iusq,
          totalQuantity: this.iutq
        })
      this.iuid = ''
      this.iusc = ''
      this.iupm = ''
      this.iuss = ''
      this.iudate = ''
      this.iupp = ''
      this.iusq = ''
      this.iutq = ''
    },
    update () {
      this.$axios
        .put('/inventories', {
          id: this.iuid,
          supplyCenter: this.iusc,
          productModel: this.iupm,
          supplyStaff: this.iuss,
          date: this.iudate,
          purchasePrice: this.iupp,
          surplusQuantity: this.iusq,
          totalQuantity: this.iutq
        })
      this.iuid = ''
      this.iusc = ''
      this.iupm = ''
      this.iuss = ''
      this.iudate = ''
      this.iupp = ''
      this.iusq = ''
      this.iutq = ''
    }
  }
}
</script>

<style>
.el-header {
  background-color: #e9eef3;
  color: #333;
  text-align: center;
}

.el-main {
  background-color: #e9eef3;
  color: #333;
  text-align: center;
  line-height: 400px;
}

body > .el-container {
  margin-bottom: 40px;
}
</style>
