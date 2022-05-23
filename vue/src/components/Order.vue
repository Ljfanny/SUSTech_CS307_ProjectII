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
            <el-button type="primary" plain @click="getAll">Order</el-button>
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
            <el-input v-model="iucn" placeholder="con number"></el-input>
          </el-col>
          <el-col :span="0.1">
            &#160;
          </el-col>
          <el-col :span="2">
            <el-input v-model="iuenterprise" placeholder="enterprise"></el-input>
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
            <el-input v-model="iuquantity" placeholder="quantity"></el-input>
          </el-col>
          <el-col :span="0.1">
            &#160;
          </el-col>
          <el-col :span="2">
            <el-input v-model="iucm" placeholder="con manager"></el-input>
          </el-col>
          <el-col :span="0.1">
            &#160;
          </el-col>
          <el-col :span="2">
            <el-input v-model="iucd" placeholder="con date"></el-input>
          </el-col>
          <el-col :span="0.1">
            &#160;
          </el-col>
          <el-col :span="2">
            <el-input v-model="iuedd" placeholder="est del date"></el-input>
          </el-col>
          <el-col :span="0.1">
            &#160;
          </el-col>
          <el-col :span="2">
            <el-input v-model="iuld" placeholder="lod date"></el-input>
          </el-col>
          <el-col :span="0.1">
            &#160;
          </el-col>
          <el-col :span="2">
            <el-input v-model="iusn" placeholder="sel number"></el-input>
          </el-col>
          <el-col :span="0.1">
            &#160;
          </el-col>
          <el-col :span="2">
            <el-input v-model="iuct" placeholder="con type"></el-input>
          </el-col>
        </el-row>
      </el-header>
    </el-container>
    <el-table :data="tableData" stripe border style="width: 100%" height="500">
      <el-table-column fixed prop="contractNumber" label="contract number" width="150"> </el-table-column>
      <el-table-column prop="enterprise" label="enterprise" width="150"> </el-table-column>
      <el-table-column prop="productModel" label="product model" width="150"> </el-table-column>
      <el-table-column prop="quantity" label="quantity" width="150"> </el-table-column>
      <el-table-column prop="contractManager" label="contract manager" width="150"> </el-table-column>
      <el-table-column prop="contractDate" label="contract date" width="150"> </el-table-column>
      <el-table-column prop="estimatedDeliveryDate" label="estimated delivery date" width="150"> </el-table-column>
      <el-table-column prop="lodgementDate" label="lodgement date" width="150"> </el-table-column>
      <el-table-column prop="salesmanNumber" label="salesman number" width="150"> </el-table-column>
      <el-table-column prop="contractType" label="contract type" width="150"> </el-table-column>
    </el-table>
  </div>
</template>

<script>
export default {
  data () {
    return {
      tableData: [],
      iucn: '',
      iuenterprise: '',
      iupm: '',
      iuquantity: '',
      iucm: '',
      iucd: '',
      iuedd: '',
      iuld: '',
      iusn: '',
      iuct: ''
    }
  },
  methods: {
    backToHome () {
      this.$router.push('Login')
    },
    getAll () {
      this.tableData = []
      this.$axios
        .get('/orders/all')
        .then((response) => { this.tableData = response.data.data })
    },
    get () {
      this.tableData = []
      this.$axios
        .get('/orders?' +
        'contractNumber=' + this.iucn +
        '&enterprise=' + this.iuenterprise +
        '&productModel=' + this.iupm +
        '&quantity=' + this.iuquantity +
        '&contractManager=' + this.iucm +
        '&contractDate=' + this.iucd +
        '&estimatedDeliveryDate=' + this.iuedd +
        '&lodgementDate=' + this.iuld +
        '&salesmanNumber=' + this.iusn +
        '&contractType=' + this.iuct)
        .then((response) => { this.tableData = response.data.data })
      this.iucn = ''
      this.iuenterprise = ''
      this.iupm = ''
      this.iuquantity = ''
      this.iucm = ''
      this.iucd = ''
      this.iuedd = ''
      this.iuld = ''
      this.iusn = ''
      this.iuct = ''
    },
    dlt () {
      this.tableData = []
      this.$axios.delete('/orders/diy?' +
        'contractNumber=' + this.iucn +
        '&enterprise=' + this.iuenterprise +
        '&productModel=' + this.iupm +
        '&quantity=' + this.iuquantity +
        '&contractManager=' + this.iucm +
        '&contractDate=' + this.iucd +
        '&estimatedDeliveryDate=' + this.iuedd +
        '&lodgementDate=' + this.iuld +
        '&salesmanNumber=' + this.iusn +
        '&contractType=' + this.iuct)
      this.iucn = ''
      this.iuenterprise = ''
      this.iupm = ''
      this.iuquantity = ''
      this.iucm = ''
      this.iucd = ''
      this.iuedd = ''
      this.iuld = ''
      this.iusn = ''
      this.iuct = ''
    },
    insert () {
      this.$axios
        .post('/staffs', {
          contractNumber: this.iucn,
          enterprise: this.iuenterprise,
          productModel: this.iupm,
          quantity: this.iuquantity,
          contractManager: this.iucm,
          contractDate: this.iucd,
          estimatedDeliveryDate: this.iuedd,
          lodgementDate: this.iuld,
          salesmanNumber: this.iusn,
          contractType: this.iuct
        })
      this.iucn = ''
      this.iuenterprise = ''
      this.iupm = ''
      this.iuquantity = ''
      this.iucm = ''
      this.iucd = ''
      this.iuedd = ''
      this.iuld = ''
      this.iusn = ''
      this.iuct = ''
    },
    update () {
      this.$axios
        .put('/staffs', {
          contractNumber: this.iucn,
          enterprise: this.iuenterprise,
          productModel: this.iupm,
          quantity: this.iuquantity,
          contractManager: this.iucm,
          contractDate: this.iucd,
          estimatedDeliveryDate: this.iuedd,
          lodgementDate: this.iuld,
          salesmanNumber: this.iusn,
          contractType: this.iuct
        })
      this.iucn = ''
      this.iuenterprise = ''
      this.iupm = ''
      this.iuquantity = ''
      this.iucm = ''
      this.iucd = ''
      this.iuedd = ''
      this.iuld = ''
      this.iusn = ''
      this.iuct = ''
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
