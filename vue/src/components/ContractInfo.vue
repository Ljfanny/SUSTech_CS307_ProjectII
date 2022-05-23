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
    <el-table :data="tableData"  stripe border style="width: 100%" height="200">
      <el-table-column fixed prop="contractNumber" label="number" width="150">
      </el-table-column>
      <el-table-column prop="contractManagerName" label="manager" width="150">
      </el-table-column>
      <el-table-column prop="enterprise" label="enterprise" width="150">
      </el-table-column>
      <el-table-column prop="supplyCenter" label="supply center" width="150">
      </el-table-column>
      </el-table>
    <el-table :data="tableData2" stripe border style="width: 100%" height="500">
      <el-table-column fixed prop="productModel" label="model" width="150">
      </el-table-column>
      <el-table-column prop="salesmanName" label="salesman" width="150">
      </el-table-column>
      <el-table-column prop="quantity" label="quantity" width="150">
      </el-table-column>
      <el-table-column prop="unitPrice" label="unit price" width="150">
      </el-table-column>
      <el-table-column prop="estimatedDeliveryDate" label="estimated date" width="150">
      </el-table-column>
      <el-table-column prop="lodgementDate" label="lodgement date" width="150">
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
export default {
  data () {
    return {
      tableData: [],
      number: '',
      tableData2: []
    }
  },
  methods: {
    backToHome () {
      this.$router.push('Login')
    },
    get () {
      this.tableData = []
      this.tableData2 = []
      this.$axios.get('/orders/getContractInfo/' + this.number).then((response) => {
        this.tableData.push(response.data.data[1])
        this.tableData2 = response.data.data
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
