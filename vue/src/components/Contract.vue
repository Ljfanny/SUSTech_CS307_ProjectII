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
            <el-button type="primary" plain @click="getAll">Contract</el-button>
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
            <el-input v-model="iuenterprise" placeholder="enterprise"></el-input>
          </el-col>
          <el-col :span="0.1">
            &#160;
          </el-col>
          <el-col :span="2">
            <el-input v-model="iucenter" placeholder="center"></el-input>
          </el-col>
        </el-row>
      </el-header>
    </el-container>
    <el-table :data="tableData" stripe border style="width: 100%" height="500">
      <el-table-column fixed prop="contractNumber" label="contract number" width="150">
      </el-table-column>
      <el-table-column prop="contractManager" label="contract manager" width="150"> </el-table-column>
      <el-table-column prop="contractDate" label="contract date" width="150"> </el-table-column>
      <el-table-column prop="enterprise" label="enterprise" width="150">
      </el-table-column>
      <el-table-column prop="center" label="center" width="150">
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
export default {
  data () {
    return {
      tableData: [],
      iucn: '',
      iucm: '',
      iucd: '',
      iuenterprise: '',
      iucenter: ''
    }
  },
  methods: {
    backToHome () {
      this.$router.push('Login')
    },
    getAll () {
      this.tableData = []
      this.$axios
        .get('/concracts/all')
        .then((response) => { this.tableData = response.data.data })
    },
    get () {
      this.tableData = []
      this.$axios
        .get('/concracts?' +
        'contractNumber=' + this.iucn +
        '&contractManager=' + this.cm +
        '&contractDate=' + this.iucd +
        '&enterprise=' + this.iuenterprise +
        '&center=' + this.iucenter)
        .then((response) => { this.tableData = response.data.data })
      this.iucn = ''
      this.iucm = ''
      this.iucd = ''
      this.iuenterprise = ''
      this.iucenter = ''
    },
    dlt () {
      this.tableData = []
      this.$axios.delete('/concracts?' +
        'contractNumber=' + this.iucn +
        '&contractManager=' + this.cm +
        '&contractDate=' + this.iucd +
        '&enterprise=' + this.iuenterprise +
        '&center=' + this.iucenter)
      this.iucn = ''
      this.iucm = ''
      this.iucd = ''
      this.iuenterprise = ''
      this.iucenter = ''
    },
    insert () {
      this.$axios
        .post('/concracts', {
          contractNumber: this.iucn,
          contractManager: this.cm,
          contractDate: this.iucd,
          enterprise: this.iuenterprise,
          center: this.iucenter
        })
      this.iucn = ''
      this.iucm = ''
      this.iucd = ''
      this.iuenterprise = ''
      this.iucenter = ''
    },
    update () {
      this.$axios
        .put('/concracts', {
          contractNumber: this.iucn,
          contractManager: this.cm,
          contractDate: this.iucd,
          enterprise: this.iuenterprise,
          center: this.iucenter
        })
      this.iucn = ''
      this.iucm = ''
      this.iucd = ''
      this.iuenterprise = ''
      this.iucenter = ''
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
