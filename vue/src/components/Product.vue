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
            <el-button type="primary" plain @click="get">select</el-button>
          </el-col>
        </el-row>
      </el-header>
      <el-header>
        <el-row>
          <el-col :span="2">
            <el-input v-model="number" placeholder="number"></el-input>
          </el-col>
        </el-row>
      </el-header>
    </el-container>
    <el-table :data="tableData" stripe border style="width: 100%" height="500">
      <el-table-column fixed prop="supplyCenter" label="id" width="150">
      </el-table-column>
      <el-table-column prop="productNumber" label="number" width="150">
      </el-table-column>
      <el-table-column prop="productModel" label="model" width="150">
      </el-table-column>
      <el-table-column prop="purchasePrice" label="price" width="150">
      </el-table-column>
      <el-table-column prop="surplusQuantity" label="surplus" width="150">
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
export default {
  data () {
    return {
      tableData: [],
      number: ''
    }
  },
  methods: {
    backToHome () {
      this.$router.push('Login')
    },
    get () {
      this.tableData = []
      this.$axios.get('/inventories/getProductByNumber/' + this.number).then((response) => {
        this.tableData = response.data.data
      })
      this.number = ''
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
