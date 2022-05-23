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
            <el-button type="primary" plain @click="getAll">Model</el-button>
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
            <el-input v-model="iunumber" placeholder="number"></el-input>
          </el-col>
          <el-col :span="0.1">
            &#160;
          </el-col>
          <el-col :span="2">
            <el-input v-model="iumodel" placeholder="model"></el-input>
          </el-col>
          <el-col :span="0.1">
            &#160;
          </el-col>
          <el-col :span="2">
            <el-input v-model="iuname" placeholder="name"></el-input>
          </el-col>
          <el-col :span="0.1">
            &#160;
          </el-col>
          <el-col :span="2">
            <el-input v-model="iuup" placeholder="unit price"></el-input>
          </el-col>
        </el-row>
      </el-header>
    </el-container>
    <el-table :data="tableData" stripe border style="width: 100%" height="500">
      <el-table-column fixed prop="id" label="id" width="150">
      </el-table-column>
      <el-table-column prop="number" label="number" width="150"> </el-table-column>
      <el-table-column prop="model" label="model" width="150"> </el-table-column>
      <el-table-column prop="name" label="name" width="150">
      </el-table-column>
      <el-table-column prop="unitPrice" label="unit price" width="150">
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
export default {
  data () {
    return {
      tableData: [],
      iuid: '',
      iuname: '',
      iunumber: '',
      iumodel: '',
      iuup: ''
    }
  },
  methods: {
    backToHome () {
      this.$router.push('Login')
    },
    getAll () {
      this.tableData = []
      this.$axios
        .get('/models/all')
        .then((response) => { this.tableData = response.data.data })
    },
    get () {
      this.tableData = []
      this.$axios
        .get('/models?' +
        'id=' + this.iuid +
        '&number=' + this.iunumber +
        '&model=' + this.iumodel +
        '&name=' + this.iuname +
        '&unitPrice=' + this.iuup)
        .then((response) => { this.tableData = response.data.data })
      this.iuid = ''
      this.iuname = ''
      this.iunumber = ''
      this.iumodel = ''
      this.iuup = ''
    },
    dlt () {
      this.tableData = []
      this.$axios.delete('/models?' +
        'id=' + this.iuid +
        '&number=' + this.iunumber +
        '&model=' + this.iumodel +
        '&name=' + this.iuname +
        '&unitPrice=' + this.iuup)
      this.iuid = ''
      this.iuname = ''
      this.iunumber = ''
      this.iumodel = ''
      this.iuup = ''
    },
    insert () {
      this.$axios
        .post('/models', {
          id: this.iuid,
          number: this.iunumber,
          model: this.iumodel,
          name: this.iuname,
          unitPrice: this.iuup
        })
      this.iuid = ''
      this.iuname = ''
      this.iunumber = ''
      this.iumodel = ''
      this.iuup = ''
    },
    update () {
      this.$axios
        .put('/models', {
          id: this.iuid,
          number: this.iunumber,
          model: this.iumodel,
          name: this.iuname,
          unitPrice: this.iuup
        })
      this.iuid = ''
      this.iuname = ''
      this.iunumber = ''
      this.iumodel = ''
      this.iuup = ''
    }
  },
  mounted () {
    this.getAll()
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
